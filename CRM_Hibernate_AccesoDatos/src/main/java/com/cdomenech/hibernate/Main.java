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

import java.util.Scanner;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class Main {

    //private static SessionFactory sessionFactory;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GestorClientes cliente = new GestorClientes();
        GestorOportunidades oportunidad = new GestorOportunidades();
        GestorActividades actividad = new GestorActividades();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        String option;

        while (!exit) {
            System.out.println("================================================");
            System.out.println("¡Bienvenido a tu CRM favorito!");
            System.out.println("¿Qué quieres hacer?");
            System.out.println("    1. Mostrar la situación actual. \n    2. Gestionar los clientes. \n    3. Gestionar las oportunidades. \n    4. Gestionar las actividades. \n    5. Salir.");
            option = sc.nextLine();
            switch (option) {
                // MOSTRAR SITUACIÓN ACTUAL
                case "1":
                    boolean exitVisualizador = false;
                    while (!exitVisualizador) {
                        System.out.println("================================================");
                        System.out.println("> Estás en el visualizador de oportunidades.");
                        System.out.println("¿Qué quieres hacer?");
                        System.out.println("    1. Mostrar la situación de tus oportunidades con un cliente. \n    2. Mostrar todas las oportunidades activas. \n    3. Mostrar las oportunidades ganadas y perdidas. \n    4. Salir al menú principal.");
                        option = sc.nextLine();
                        switch (option) {
                            case "1":
                                System.out.println("================================================");
                                Integer idCliente = 0;
                                boolean validCliente = false;
                                while (!validCliente) {
                                    System.out.println("Clientes actuales: ");
                                    System.out.println("==========");
                                    cliente.getAllEmailClientes();
                                    System.out.println("Introduce el email de uno de los clientes anteriores: ");
                                    String emailCliente = sc.nextLine();
                                    idCliente = cliente.checkEmailClienteExists(emailCliente);
                                    if (idCliente == null) {
                                        System.out.println("Ese cliente no existe.");
                                    } else {
                                        validCliente = true;
                                    }
                                }
                                System.out.println("Estas son las oportunidades que tienes con el cliente seleccionado: ");
                                oportunidad.getAllOportunidadesFromACliente(idCliente);
                                break;

                            case "2":
                                System.out.println("================================================");
                                System.out.println("Estas son todas las oportunidades activas: ");
                                oportunidad.getActiveOportunidades();
                                break;
                            case "3":
                                System.out.println("================================================");
                                oportunidad.getNonactiveOportunidades();
                                break;
                            case "4":
                                exitVisualizador = true;
                                break;
                            default:
                                System.out.println("Selecciona una de las opciones.");
                        }
                    }
                    break;
                // GESTOR DE CLIENTES
                case "2":
                    boolean exitGestorClientes = false;
                    while (!exitGestorClientes) {
                        System.out.println("================================================");
                        System.out.println("> Estás en el gestor de clientes.");
                        System.out.println("¿Qué quieres hacer?");
                        System.out.println("    1. Listar clientes. \n    2. Añadir un nuevo cliente. \n    3. Editar un cliente. \n    4. Eliminar a un cliente. \n    5. Salir al menú principal.");
                        option = sc.nextLine();
                        switch (option) {
                            // LISTAR CLIENTES
                            case "1":
                                System.out.println("================================================");
                                System.out.println("Clientes actuales: ");
                                System.out.println("==========");
                                cliente.getAllClientes();
                                break;

                            // CREAR NUEVO CLIENTE
                            case "2":
                                System.out.println("================================================");
                                System.out.println("Vas a crear un nuevo cliente. Recuerda: Todos los campos son obligatorios.");
                                String name = "";
                                String surname = "";
                                String company = "";
                                String number = "";
                                String email = "";
                                boolean validClienteData = false;
                                while (!validClienteData) {
                                    System.out.println("Introduce el nombre del cliente: ");
                                    name = sc.nextLine();
                                    System.out.println("Introduce los apellidos del cliente: ");
                                    surname = sc.nextLine();
                                    System.out.println("Introduce la empresa del cliente: ");
                                    company = sc.nextLine();
                                    System.out.println("Introduce el teléfono del cliente: ");
                                    number = sc.nextLine();

                                    validClienteData = cliente.dataChecker(name, surname, company, number);
                                }
                                boolean validEmail = false;
                                while (!validEmail) {
                                    System.out.println("Introduce el email del cliente: ");
                                    email = sc.nextLine();
                                    validEmail = cliente.isValidEmail(email);
                                }

                                cliente.newFullCliente(name, surname, company, number, email);
                                break;

                            // EDITAR CLIENTE
                            case "3":
                                System.out.println("================================================");
                                System.out.println("Vas a editar un cliente.");
                                System.out.println("============================");
                                System.out.println("Clientes actuales: ");
                                System.out.println("==========");
                                cliente.getAllClientes();
                                System.out.println("Introduce el ID del cliente que quieres modificar: ");
                                String id_cliente = sc.nextLine();
                                cliente.getSpecificCliente(Integer.parseInt(id_cliente));
                                boolean moreChange;
                                do {
                                    moreChange = false;
                                    System.out.println("¿Qué quieres cambiar?");
                                    System.out.println("1. Nombre  2. Apellidos  3. Empresa  4. Teléfono  5. Email");
                                    String opt = sc.nextLine();
                                    System.out.println("Introduce el nuevo dato: ");
                                    String newData = sc.nextLine();
                                    cliente.editCliente(Integer.parseInt(id_cliente), newData, opt);
                                    System.out.println("¿Quieres cambiar algo más? S/N");
                                    if (sc.nextLine().equalsIgnoreCase("s")) {
                                        moreChange = true;
                                    }
                                } while (moreChange);
                                System.out.println("Cliente editado correctamente");
                                break;
                            // ELIMINAR CLIENTE
                            case "4":
                                System.out.println("================================================");
                                System.out.println("Vas a eliminar un cliente.");
                                System.out.println("============================");
                                System.out.println("Clientes actuales: ");
                                System.out.println("==========");
                                cliente.getAllClientes();
                                System.out.println("Introduce el ID del cliente que quieres eliminar: ");
                                String idClienteDelete = sc.nextLine();
                                System.out.println("Este es el cliente que va a eliminar: ");
                                cliente.getSpecificCliente(Integer.parseInt(idClienteDelete));
                                System.out.println("¿Estás seguro de que quieres eliminar este cliente? S/N");
                                if (sc.nextLine().equalsIgnoreCase("s")) {
                                    cliente.deleteCliente(Integer.parseInt(idClienteDelete));
                                } else {
                                    System.out.println("El cliente no ha sido eliminado.");
                                }
                                break;

                            // SALIR
                            case "5":
                                exitGestorClientes = true;
                                break;
                            default:
                                System.out.println("Selecciona una de las opciones.");
                        }
                    }
                    break;

                // GESTOR DE OPORTUNIDADES
                case "3":
                    boolean exitGestorOportunidades = false;
                    while (!exitGestorOportunidades) {
                        System.out.println("================================================");
                        System.out.println("> Estás en el gestor de oportunidades.");
                        System.out.println("¿Qué quieres hacer?");
                        // TODO añada el email del cliente. Si existe continua... si no, crea un nuevo cliente con ese email y los demás campos vacíos
                        System.out.println("    1. Listar oportunidades. \n    2. Añadir una nueva oportunidad a un cliente. \n    3. Cambiar el estado de una oportunidad. \n    4. Eliminar una oportunidad. \n    5. Salir al menú principal.");
                        option = sc.nextLine();
                        switch (option) {
                            case "1":
                                System.out.println("================================================");
                                System.out.println("Oportunidades actuales: ");
                                System.out.println("==========");
                                oportunidad.getAllOportunidades();
                                break;
                            case "2":
                                System.out.println("================================================");
                                System.out.println("Vas a crear una nueva oportunidad.");
                                System.out.println("============================");
                                System.out.println("Clientes actuales: ");
                                System.out.println("==========");
                                cliente.getAllEmailClientes();
                                String emailCliente = "";
                                Integer idCliente;
                                String description = "";
                                String value = "";
                                String date = "";
                                String priority = "";

                                boolean validEmail = false;
                                while (!validEmail) {
                                    System.out.println("Introduce el email del cliente con el que vas a crear una oportunidad: ");
                                    emailCliente = sc.nextLine();
                                    validEmail = cliente.isValidEmail(emailCliente);
                                }
                                idCliente = cliente.checkEmailClienteExists(emailCliente);

                                if (idCliente == null) {
                                    System.out.println("Ese cliente no existe, ¿Quieres crearlo? S/N");
                                    String answer = sc.nextLine();
                                    if (answer.equalsIgnoreCase("s")) {
                                        cliente.newBasicCliente(emailCliente);
                                    } else {
                                        System.out.println("No se ha creado un nuevo cliente.");
                                        break;
                                    }
                                }
                                boolean validOportunidadData = false;
                                while (!validOportunidadData) {
                                    System.out.println("Introduce la descripción de la oportunidad: ");
                                    description = sc.nextLine();
                                    System.out.println("Introduce su valor: ");
                                    value = sc.nextLine();
                                    System.out.println("Introduce su fecha de validez, usa el formato DD/MM/AAAA : ");
                                    date = sc.nextLine();
                                    System.out.println("Introduce su prioridad (ALTA, MEDIA o BAJA): ");
                                    priority = sc.nextLine();
                                    if (oportunidad.dataChecker(description, value, date, priority)) {
                                        validOportunidadData = true;
                                    } else {
                                        System.out.println("Uno o más datos no eran correctos. Por favor, sigue las indicaciones.");
                                    }
                                }
                                oportunidad.newOportunidad(idCliente, description, value, date, priority);
                                break;

                            case "3":
                                System.out.println("================================================");
                                System.out.println("Vas a editar el estado de una oportunidad.");
                                System.out.println("============================");
                                System.out.println("Oportunidades actuales: ");
                                oportunidad.getAllOportunidades();
                                System.out.println("Introduce el ID de la oportunidad que quieres modificar: ");
                                String idOportunidad = sc.nextLine();
                                oportunidad.getSpecificOportunidad(Integer.parseInt(idOportunidad));
                                String state = "";
                                do {
                                    System.out.println("¿Cuál es el nuevo estado? NUEVA, CALIFICADA, PROPUESTA, GANADA, PERDIDA");
                                    state = sc.nextLine();
                                } while (!oportunidad.updateState(Integer.parseInt(idOportunidad), state));
                                break;
                            case "4":
                                System.out.println("================================================");
                                System.out.println("Vas a eliminar una oportunidad.");
                                System.out.println("============================");
                                System.out.println("Oportunidades actuales: ");
                                System.out.println("==========");
                                oportunidad.getAllOportunidades();
                                System.out.println("Introduce el ID de la oportunidad que quieres eliminar: ");
                                String idOportunidadDelete = sc.nextLine();
                                System.out.println("Este es la oportunidad que va a eliminar: ");
                                oportunidad.getSpecificOportunidad(Integer.parseInt(idOportunidadDelete));
                                System.out.println("¿Estás seguro de que quieres eliminar esta oportunidad? S/N");
                                if (sc.nextLine().equalsIgnoreCase("s")) {
                                    oportunidad.deleteOportunidad(Integer.parseInt(idOportunidadDelete));
                                } else {
                                    System.out.println("La oportunidad no ha sido eliminado.");
                                }
                                break;
                            case "5":
                                exitGestorOportunidades = true;
                                break;
                            default:
                                System.out.println("Selecciona una de las opciones.");
                        }
                    }
                    break;

                // GESTOR DE ACTIVIDADES
                case "4":
                    boolean exitGestorActividades = false;
                    while (!exitGestorActividades) {
                        System.out.println("================================================");
                        System.out.println("> Estás en el gestor de actividades.");
                        System.out.println("¿Qué quieres hacer?");
                        System.out.println("    1. Listar actividades. \n    2. Añadir una nueva actividad a una oportunidad. \n    3. Editar una actividad. \n    4. Eliminar una actividad. \n    5. Salir al menú principal.");
                        option = sc.nextLine();
                        switch (option) {
                            case "1":
                                System.out.println("================================================");
                                System.out.println("Actividades actuales: ");
                                System.out.println("==========");
                                actividad.getAllActividades();
                                break;
                            case "2":
                                System.out.println("================================================");
                                System.out.println("Vas a crear una nueva actividad.");
                                System.out.println("============================");
                                System.out.println("Oportunidades actuales: ");
                                System.out.println("==========");
                                oportunidad.getAllOportunidades();
                                String idOportunidad;
                                String type = "";
                                String description = "";
                                String date = "";

                                System.out.println("Introduce el ID de la oportunidad en la que quieres añadir una actividad: ");
                                idOportunidad = sc.nextLine();
                                oportunidad.getSpecificOportunidad(Integer.parseInt(idOportunidad));
                                boolean validActividadData = false;
                                while (!validActividadData) {
                                    System.out.println("Introduce el tipo de actividad: ");
                                    type = sc.nextLine();
                                    System.out.println("Introduce una descripción: ");
                                    description = sc.nextLine();
                                    System.out.println("Introduce su fecha de validez, usa el formato DD/MM/AAAA : ");
                                    date = sc.nextLine();
                                    if (actividad.dataChecker(type, description, date)) {
                                        validActividadData = true;
                                    } else {
                                        System.out.println("Uno o más datos no eran correctos. Por favor, sigue las indicaciones.");
                                    }
                                }
                                actividad.newActividad(Integer.parseInt(idOportunidad), type, description, date);
                                break;

                            case "3":
                                System.out.println("================================================");
                                System.out.println("Vas a editar una actividad.");
                                System.out.println("============================");
                                System.out.println("Actividades actuales: ");
                                actividad.getAllActividades();
                                System.out.println("Introduce el ID de la que quieres modificar: ");
                                String id_cliente = sc.nextLine();
                                actividad.getSpecificActividad(Integer.parseInt(id_cliente));
                                boolean moreChange;
                                do {
                                    moreChange = false;
                                    System.out.println("¿Qué quieres cambiar?");
                                    System.out.println("1. Tipo  2. Descripción  3. Fecha");
                                    String opt = sc.nextLine();
                                    System.out.println("Introduce el nuevo dato: ");
                                    String newData = sc.nextLine();
                                    actividad.editActividad(Integer.parseInt(id_cliente), newData, opt);
                                    System.out.println("¿Quieres cambiar algo más? S/N");
                                    if (sc.nextLine().equalsIgnoreCase("s")) {
                                        moreChange = true;
                                    }
                                } while (moreChange);
                                System.out.println("Actividad editada correctamente");
                                break;
                            case "4":
                                System.out.println("================================================");
                                System.out.println("Vas a eliminar una actividad.");
                                System.out.println("============================");
                                System.out.println("Actividades actuales: ");
                                actividad.getAllActividades();
                                System.out.println("Introduce el ID de la actividad que quieres eliminar: ");
                                String idActividadDelete = sc.nextLine();
                                System.out.println("Este es la actividad que va a eliminar: ");
                                actividad.getSpecificActividad(Integer.parseInt(idActividadDelete));
                                System.out.println("¿Estás seguro de que quieres eliminar esta actividad? S/N");
                                if (sc.nextLine().equalsIgnoreCase("s")) {
                                    actividad.deleteActividad(Integer.parseInt(idActividadDelete));
                                } else {
                                    System.out.println("La actividad no ha sido eliminada.");
                                }
                                break;
                            case "5":
                                exitGestorActividades = true;
                                break;
                            default:
                                System.out.println("Selecciona una de las opciones.");
                        }
                    }
                    break;

                // SALIR Y FINALIZAR EL PROGRAMA
                case "5":
                    System.out.println("Hasta luego.");
                    cliente.closeSession();
                    oportunidad.closeSession();
                    actividad.closeSession();
                    exit = true;
                    break;
                default:
                    System.out.println("Selecciona una de las opciones.");
            }
        }
    }
}
