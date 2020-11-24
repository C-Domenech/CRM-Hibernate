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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Actividad;
import models.Oportunidad;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class GestorActividades {
    // Attributes
    private Session s;
    private Transaction t;

    /**
     * Constructor
     */
    public GestorActividades() {
        this.s = HibernateUtil.getSessionFactory().openSession();
        this.t = s.beginTransaction();
    }

    /**
     * Prints all the activities and activities of a client
     */
    public void getAllActividades() {
        List result = this.s.createQuery("FROM Actividad").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            models.Actividad act = (models.Actividad) it.next();
            System.out.println(act.toString());
        }
    }

    /**
     * Prints a specific activity
     * 
     * @param id_actividad
     */
    public void getSpecificActividad(Integer id_actividad) {
        if (checkActividadExists(id_actividad)) {
            models.Actividad act = s.get(Actividad.class, id_actividad);
            System.out.println(act.toString());
        }
    }

    /**
     * Creates an activity with all the information given by the user
     * 
     * @param id_oportunidad
     * @param type
     * @param description
     * @param dateStr String date it is going to be converted to Date type
     */
    public void newActividad(Integer id_oportunidad, String type, String description, String dateStr) {
        try {
            checkTransaction();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateStr);
            models.Oportunidad op = this.s.get(Oportunidad.class, id_oportunidad);
            models.Actividad actividad = new models.Actividad(op, type, description, date);
            this.s.save(actividad);
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

    /**
     * Method that edit an activity
     * 
     * @param id_actividad id of the activity
     * @param newData
     * @param option
     */
    public void editActividad(Integer id_actividad, String newData, String option) {
        if (checkActividadExists(id_actividad)) {
            try {
                checkTransaction();
                models.Actividad actividad = s.get(Actividad.class, id_actividad);
                switch (option) {
                    case "1":
                        actividad.setTipo(newData);
                        break;
                    case "2":
                        actividad.setDescripcion(newData);
                        break;
                    case "3":
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = sdf.parse(newData);
                        actividad.setFecha(date);
                }
                s.update(actividad);
                t.commit();
            } catch (ParseException ex) {
                Logger.getLogger(GestorActividades.class.getName()).log(Level.SEVERE, null, ex);
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
     * Method that remove an activity
     * 
     * @param id_actividad
     */
    public void deleteActividad(Integer id_actividad) {
        try {
            checkTransaction();
            Query query = s.createQuery("DELETE Actividad WHERE id_actividad = :idActividad");
            query.setParameter("idActividad", id_actividad);
            int rowAffected = query.executeUpdate();
            t.commit();
            if (rowAffected > 0) {
                System.out.println("Actividad eliminada correctamente.");
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
     * Check that the user have written valid information (every field is necessary)
     * 
     * @param type
     * @param description
     * @param dateStr
     * @return
     */
    public boolean dataChecker(String type, String description, String dateStr) {
        boolean isChecked = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateStr);
            if (!type.isBlank() && !description.isBlank()) {
                isChecked = true;
            } else {
                System.out.println("Recuerda: Todos los campos son obligatorios.");
            }
        } catch (ParseException ex) {
            Logger.getLogger(GestorActividades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isChecked;
    }

    /**
     * Check that the activity exists
     * 
     * @param id_actividad
     * @return
     */
    public boolean checkActividadExists(Integer id_actividad) {
        boolean actividadExists = false;
        models.Actividad actividad = s.get(Actividad.class, id_actividad);
        if (actividad != null) {
            actividadExists = true;
        } else {
            System.out.println("Esa actividad no existe.");
        }
        return actividadExists;
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
