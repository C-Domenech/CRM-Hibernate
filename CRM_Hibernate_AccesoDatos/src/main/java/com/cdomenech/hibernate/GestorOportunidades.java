/*
 * Copyright (C) 2020 Cristina Domenech <linkedin.com/in/c-domenech/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cdomenech.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Cliente;
import models.Oportunidad;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class GestorOportunidades {

    // Attributes
    private static Session s;
    private static Transaction t;

    /**
     * Constructor
     */
    public GestorOportunidades() {
        this.s = HibernateUtil.getSessionFactory().openSession();
        this.t = s.beginTransaction();
    }

    /**
     * Prints all the opportunities and activities of a client
     *
     * @param id_cliente
     */
    public void getAllOportunidadesFromACliente(Integer id_cliente) {
        models.Cliente cliente = this.s.get(Cliente.class, id_cliente);
        Iterator it = cliente.getOportunidades().iterator();
        while (it.hasNext()) {
            System.out.println("Oportunidad: ");
            models.Oportunidad op = (models.Oportunidad) it.next();
            System.out.println(op.toString());
            Iterator it2 = op.getActividades().iterator();
            boolean firstAct = false;
            while (it2.hasNext()) {
                if (!firstAct) {
                    System.out.println("Actividades en esta oportunidad: ");
                    firstAct = true;
                }
                models.Actividad act = (models.Actividad) it2.next();
                System.out.println(act.toString());
            }
        }
    }

    /**
     * Prints all the opportunities
     */
    public void getAllOportunidades() {
        List result = this.s.createQuery("FROM Oportunidad").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            models.Oportunidad op = (models.Oportunidad) it.next();
            System.out.println(op.toString());
        }
    }

    /**
     * Prints a specific opportunities
     *
     * @param id_oportunidad
     */
    public void getSpecificOportunidad(Integer id_oportunidad) {
        if (checkOportunidadExists(id_oportunidad)) {
            models.Oportunidad op = this.s.get(Oportunidad.class, id_oportunidad);
            System.out.println(op.toString());
        }
    }

    /**
     * Prints active opportunities not win or lost
     */
    public void getActiveOportunidades() {
        List result = this.s.createQuery("FROM Oportunidad WHERE estado = 'NUEVA' OR estado = 'CALIFICADA' OR estado = 'PROPUESTA'").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            models.Oportunidad op = (models.Oportunidad) it.next();
            System.out.println(op.toString());
        }

    }

    /**
     * Prints all winned or lost opportunities
     */
    public void getNonactiveOportunidades() {
        List result;
        result = this.s.createQuery("FROM Oportunidad WHERE estado = 'GANADA'").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            System.out.println("============================");
            System.out.println("Oportunidades ganadas:");
            System.out.println("==========");
            models.Oportunidad op = (models.Oportunidad) it.next();
            System.out.println(op.toString());
        }
        result = this.s.createQuery("FROM Oportunidad WHERE estado = 'PERDIDA'").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            System.out.println("============================");
            System.out.println("Oportunidades perdidas:");
            System.out.println("==========");
            models.Oportunidad op = (models.Oportunidad) it.next();
            System.out.println(op.toString());
        }
    }

    /**
     * Creates an opportunity with all the information given by the user
     *
     * @param idCliente
     * @param description
     * @param valueStr String value it is going to be converted to Big_Decimal
     * @param dateStr String date it is going to be converted to Date type
     * @param priority
     */
    public void newOportunidad(Integer idCliente, String description, String valueStr, String dateStr, String priority) {
        GestorClientes gc = new GestorClientes();
        if (gc.checkClienteExists(idCliente)) {
            try {
                checkTransaction();
                BigDecimal value = new BigDecimal(valueStr);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(dateStr);
                Cliente cliente = (Cliente) this.s.get(Cliente.class, idCliente);
                models.Oportunidad oportunidad = new models.Oportunidad(cliente, description, value, date, priority);
                this.s.save(oportunidad);
                this.t.commit();
            } catch (ParseException ex) {
                Logger.getLogger(GestorOportunidades.class.getName()).log(Level.SEVERE, null, ex);
            } catch (HibernateException ex) {
                if (t != null) {
                    t.rollback();
                    ex.printStackTrace();
                    System.out.println("Algo fue mal.");
                }
            }
        }
    }

    /**
     * Check that the user have written valid information (every field is
     * necessary)
     *
     * @param description
     * @param valueStr
     * @param dateStr
     * @param priority
     * @return
     */
    public boolean dataChecker(String description, String valueStr, String dateStr, String priority) {
        boolean isChecked = false;
        try {
            BigDecimal value = new BigDecimal(valueStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateStr);

            if (!description.isBlank() && (priority.equalsIgnoreCase("BAJA") || priority.equalsIgnoreCase("MEDIA") || priority.equalsIgnoreCase("ALTA"))) {
                isChecked = true;
            } else {
                System.out.println("Recuerda: Todos los campos son obligatorios.");
            }

        } catch (ParseException ex) {
            Logger.getLogger(GestorOportunidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isChecked;
    }

    /**
     * Update an opportunity to another state only selected states are valid
     *
     * @param id_oportunidad
     * @param state
     * @return
     */
    public boolean updateState(Integer id_oportunidad, String state) {
        boolean dataChanged = false;
        if (checkOportunidadExists(id_oportunidad)) {
            //NUEVO, CALIFICADO, PROPUESTA, GANADA, PERDIDA 
            try {
                if (state.equalsIgnoreCase("NUEVA") || state.equalsIgnoreCase("CALIFICADA") || state.equalsIgnoreCase("PROPUESTA") || state.equalsIgnoreCase("GANADA") || state.equalsIgnoreCase("PERDIDA")) {
                    checkTransaction();
                    Oportunidad op = (Oportunidad) s.get(Oportunidad.class, id_oportunidad);
                    op.setEstado(state.toUpperCase());
                    s.update(op);
                    t.commit();
                    dataChanged = true;
                    System.out.println("Estado cambiado correctamente.");
                } else {
                    System.out.println("El estado introducido no es correcto. Sigue las indicaciones.");
                }
            } catch (HibernateException ex) {
                if (t != null) {
                    t.rollback();
                    ex.printStackTrace();
                    System.out.println("Algo fue mal.");
                }
            }
        }
        return dataChanged;
    }

    /**
     * Remove an opportunity
     *
     * @param id_oportunidad
     */
    public void deleteOportunidad(Integer id_oportunidad) {
        try {
            checkTransaction();
            Query query = s.createQuery("DELETE Oportunidad WHERE id_oportunidad = :idOportunidad");
            query.setParameter("idOportunidad", id_oportunidad);
            int rowAffected = query.executeUpdate();
            t.commit();
            if (rowAffected > 0) {
                System.out.println("Oportunidad eliminada correctamente.");
            } else {
                System.out.println("Algo fue mal.");
            }
        } catch (HibernateException ex) {
            if (t != null) {
                t.rollback();
                ex.printStackTrace();
                System.out.println("Algo fue mal.");
            }
        }
    }

    /**
     * Check than an opportunity exists
     *
     * @param id_oportunidad
     * @return
     */
    public boolean checkOportunidadExists(Integer id_oportunidad) {
        boolean opExists = false;
        models.Oportunidad oportunidad = s.get(Oportunidad.class, id_oportunidad);
        if (oportunidad != null) {
            opExists = true;
        } else {
            System.out.println("Esa oportunidad no existe.");
        }
        return opExists;
    }

    /**
     * Check if the transaction is active
     */
    public void checkTransaction() {
        if (!t.isActive()) {
            this.t = s.beginTransaction();
        }
    }

    /**
     * Close session at the end of the program
     */
    public void closeSession() {
        this.s.close();
    }

}
