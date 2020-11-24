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

import java.util.Iterator;
import java.util.List;
import models.Cliente;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class GestorClientes {
    // Attributes
    private static Session s;
    private static Transaction t;

    /**
     * Constructor
     */
    public GestorClientes() {
        this.s = HibernateUtil.getSessionFactory().openSession();
        this.t = s.beginTransaction();
    }

    /**
     * Method that prints all the clients in the database
     */
    public void getAllClientes() {
        List result = this.s.createQuery("FROM Cliente").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            Cliente cliente = (Cliente) it.next();
            System.out.println(cliente.toString());

        }
    }

    /**
     * Method that prints a specific client
     * 
     * @param id_cliente
     */
    public void getSpecificCliente(Integer id_cliente) {
        if (checkClienteExists(id_cliente)) {
            Cliente cliente = (Cliente) s.get(Cliente.class, id_cliente);
            System.out.println(cliente.toString());
        }
    }

    /**
     * Method that prints all the clients' email
     */
    public void getAllEmailClientes() {
        List result = this.s.createQuery("FROM Cliente").list();
        for (Iterator it = result.iterator(); it.hasNext();) {
            Cliente cliente = (Cliente) it.next();
            System.out.println(cliente.emailToString());
        }
    }

    /**
     * Creates a client with all the information given by the user
     * 
     * @param name
     * @param surname
     * @param company
     * @param number
     * @param email
     */
    public void newFullCliente(String name, String surname, String company, String number, String email) {
        try {
            checkTransaction();
            models.Cliente cliente = new models.Cliente(name, surname, company, number, email);
            this.s.save(cliente);

            this.t.commit();
            System.out.println("> Cliente creado correctamente.");
        } catch (ConstraintViolationException ex) {
            System.out.println("Ese cliente ya existe.");
        } catch (HibernateException ex) {
            if (t != null) {
                t.rollback();
                ex.printStackTrace();
                System.out.println("Algo fue mal.");
            }
        }
    }

    /**
     * When the user is creating a new opportunity with a client that doesn't exists
     * they can create a basic user with the email
     * 
     * @param email
     */
    public void newBasicCliente(String email) {
        if (isValidEmail(email)) {
            checkTransaction();
            try {

                models.Cliente cliente = new models.Cliente(email);
                this.s.save(cliente);
                this.t.commit();
                System.out.println("> Cliente creado correctamente.");

            } catch (ConstraintViolationException ex) {
                System.out.println("Ese cliente ya existe.");
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
     * Method that edit clients' data
     * 
     * @param id_cliente id of the client
     * @param newData new data that change
     * @param option kind of data
     */
    public void editCliente(Integer id_cliente, String newData, String option) {
        if (checkClienteExists(id_cliente)) {
            try {
                checkTransaction();
                models.Cliente cliente = s.get(Cliente.class, id_cliente);
                switch (option) {
                    case "1":
                        cliente.setNombre(newData);
                        break;
                    case "2":
                        cliente.setApellidos(newData);
                        break;
                    case "3":
                        cliente.setEmpresa(newData);
                        break;
                    case "4":
                        cliente.setTelefono(newData);
                        break;
                    case "5":
                        cliente.setEmail(newData);
                        break;
                }
                s.update(cliente);
                t.commit();
            } catch (ConstraintViolationException ex) {
                System.out.println("Ese email ya pertenece a otro cliente.");
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
     * Remove a client from the database
     * 
     * @param id_cliente
     */
    public void deleteCliente(Integer id_cliente) {
        try {
            checkTransaction();
            Query query = s.createQuery("DELETE Cliente WHERE id_cliente = :idCliente");
            query.setParameter("idCliente", id_cliente);
            int rowAffected = query.executeUpdate();
            t.commit();
            if (rowAffected > 0) {
                System.out.println("Cliente eliminado correctamente.");
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
     * @param name
     * @param surname
     * @param company
     * @param number
     * @return
     */
    public boolean dataChecker(String name, String surname, String company, String number) {
        boolean isChecked = false;
        if (!name.isBlank() && !surname.isBlank() && !company.isBlank() && !number.isBlank()) {
            isChecked = true;
        } else {
            System.out.println("Recuerda: Todos los campos son obligatorios.");
        }
        return isChecked;
    }

    /**
     * Check that the email follow a pattern of email example@domain.com
     * 
     * @param email
     * @return
     */
    public boolean isValidEmail(String email) {
        // Email Address Regular Expression
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        boolean validEmail = email.matches(regex);
        if (!validEmail) {
            System.out.println("Ese email no es válido. Ejemplo: cliente@dominio.com");
        }
        return validEmail;
    }

    /**
     * Check that client exists and return the id
     * 
     * @param email
     * @return
     */
    public Integer checkEmailClienteExists(String email) {
        Integer id_cliente;
        List<Integer> result;
        Query query = this.s.createQuery("SELECT c.id_cliente FROM Cliente c WHERE c.email = :email");
        query.setParameter("email", email);
        result = query.list();
        if (result.isEmpty()) {
            id_cliente = null;
        } else {
            id_cliente = result.get(0);
        }

        return id_cliente;
    }

    /**
     * Method that checks if the client exists
     * 
     * @param id_cliente
     * @return 
     */
    public boolean checkClienteExists(Integer id_cliente) {
        boolean clienteExists = false;
        models.Cliente cliente = s.get(Cliente.class, id_cliente);
        if (cliente != null) {
            clienteExists = true;
        } else {
            System.out.println("Ese cliente no existe.");
        }
        return clienteExists;
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
