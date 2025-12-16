package com.barasiqueira.ui;

import com.barasiqueira.modelo.Coche;
import com.barasiqueira.modelo.Matricula;
import com.barasiqueira.modelo.Parte;
import com.barasiqueira.servicio.GestionCoche;
import com.barasiqueira.servicio.GestionParte;
import com.barasiqueira.servicio.GestionTaller;
import com.barasiqueira.util.CodigoInvalidoException;
import com.barasiqueira.util.ImporteInvalidoException;
import com.barasiqueira.util.MatriculaInvalidaException;
import com.barasiqueira.util.ValidacionUtil;

import java.util.Scanner;

public class Consola {

    private final static String NUEVO_COCHE = "1";
    private final static String AGREGAR_PARTE = "2";

    private final static String CREAR = "1";
    private final static String LISTAR = "2";
    private final static String MOSTRAR = "3";
    private final static String ELIMINAR = "4";
    private final static String SALIR = "5";

    private final Scanner entrada;
    private final GestionCoche gestionCoche;
    private final GestionParte gestionParte;
    private final GestionTaller gestionTaller;

    public Consola(){
        entrada = new Scanner(System.in);
        gestionCoche = new GestionCoche();
        gestionParte = new GestionParte();
        gestionTaller = new GestionTaller(gestionCoche, gestionParte);
    }

    public String leerOpcionMenu() {
        System.out.println("\n============================");
        System.out.println(" Gerencinador Taller Mecânico ");
        System.out.println(" =============================");
        System.out.println(" Elija una opción ");
        System.out.println(" =============================");
        System.out.println(" 1 - Crear ");
        System.out.println(" 2 - Listar ");
        System.out.println(" 3 - Mostrar ");
        System.out.println(" 4 - Eliminar ");
        System.out.println(" 5 - salir ");
        System.out.print(" Opción: ");

        return entrada.nextLine();
    }

    public boolean ejecutarMenu(String opcion) {
        switch (opcion) {
            case CREAR -> crearCocheYParte();
            case LISTAR -> listarTodosCochesRegistrados();
            case MOSTRAR -> mostrarCochePorMatricula();
            case ELIMINAR -> eliminarParte();
            case SALIR -> salirDelPrograma();

            default -> throw new IllegalArgumentException(" ¡Opción inválida! seleccione de 1 a 5.");
        }
        return  true;
    }

    private void crearCocheYParte() {
        boolean continuarSubMenu = true;

        while (continuarSubMenu) {
            System.out.println(" ¿Desea crear un coche nuevo o agregar una parte a uno existente?");
            System.out.print(" 1 - Nuevo coche\n");
            System.out.print(" 2 - Agregar parte a coche existente\n");
            System.out.print(" Opción: ");
            String seleccion = entrada.nextLine().trim();

            try {
                switch (seleccion) {
                    case NUEVO_COCHE -> crearNuevoCocheConPartes();
                    case AGREGAR_PARTE -> agregarPartesACochesExistentes();
                    default -> throw new IllegalArgumentException(" Opcion inválida, vuelva a intentarlo.");
                }

                continuarSubMenu = false;
            } catch (MatriculaInvalidaException | CodigoInvalidoException | IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());

                boolean intentarOtraVez = confirmarAccion(" ¿Desea intentar nuevamente? ");
                if (!intentarOtraVez) {
                    continuarSubMenu = false;
                }

            }

        }

    }

    private void listarTodosCochesRegistrados() {
        boolean repetir = true;
        while (repetir) {
            try {
                gestionTaller.listarTodosCochesYPartes();
            } catch (IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());
            }
             boolean volverAlMenu = confirmarAccion(" ¿Desea volver al menu principal? (s/n):");
            repetir = !volverAlMenu;
        }
    }


    private void mostrarCochePorMatricula() {
        boolean continuar = true;

        while (continuar) {
            try {
                Matricula matriculaValida = leerYValidarMatricula(" Introduzca la matrícula del coche: ");

                Coche coche = gestionCoche.buscar(matriculaValida);

                System.out.println(coche.matricula());
                gestionTaller.mostrarPartesDeCoche(coche.matricula());

                continuar = confirmarAccion(" \n ¿Desea buscar otro coche? (s/n): ");

            } catch (MatriculaInvalidaException | IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());

                boolean intentarOtraVez = confirmarAccion(" ¿Desea intentar con otra matrícula? (s/n):");
                if (!intentarOtraVez) {
                    continuar = false;
                }
            }
        }
    }

    private void eliminarParte() {
        boolean continuar = true;

        while (continuar) {
            System.out.println(" ¿Cómo desea eliminar la parte?");
            System.out.print(" 1 - Por matrícula\n");
            System.out.print(" 2 - Globalmente\n");
            System.out.print(" Opción: ");
            String opcion = entrada.nextLine();

            try {
                switch (opcion) {
                    case "1" -> eliminarPartePorCoche();
                    case "2" -> eliminarParteGlobal();
                    default -> throw new IllegalArgumentException(" Opción inválida, vuela a intentarlo.");
                }
                continuar = confirmarAccion(" ¿Desea eliminar otra parte? (s/n): ");

            } catch (MatriculaInvalidaException | CodigoInvalidoException | IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());

                boolean intentarOtraVez = confirmarAccion(" ¿Desea intentar nuevamente? (s/n): ");
                if (!intentarOtraVez) {
                    continuar = false;
                }
            }

        }
    }

    private boolean salirDelPrograma() {
        boolean salir = confirmarAccion(" \n¿Desea salir del programa? (s/n): ");
        if (salir) {
            System.out.println(" Programa Finalizado. ¡Hasta luego!");
            System.exit(0);
            return false;
        } else  {
            return true;
        }
    }

    private void crearNuevoCocheConPartes() {
        boolean continuarCreandoCoche = true;

        while (continuarCreandoCoche) {
            try {
                Matricula matriculaValida = leerYValidarMatricula(" Introduza la matricula del coche: ");

                Coche cocheNuevo = new Coche(matriculaValida);
                gestionCoche.crear(cocheNuevo);

                agregarNuevaPartesACoches(cocheNuevo);

                System.out.println(" \nCoche creado correctamente: " + cocheNuevo.matricula());
                boolean crearOtro = confirmarAccion(" \n ¿Desea crear otro coche? (s/n):");

                if (!crearOtro) {
                    continuarCreandoCoche = false;
                }

            } catch (MatriculaInvalidaException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                boolean intentarDeNuevo = confirmarAccion(" ¿Desea intentar nuevamente esta acción? (s/n):");
                if (!intentarDeNuevo) {
                    continuarCreandoCoche = false;
                }
            }
        }

    }

    private void agregarPartesACochesExistentes() {
        boolean agregarParte = true;

        while (agregarParte) {
            try {
                Matricula matriculaValida = leerYValidarMatricula(" Introduzca la matrícula del coche existente: ");

                Coche coche = gestionCoche.buscar(matriculaValida);
                agregarNuevaPartesACoches(coche);

                agregarParte = confirmarAccion(" \n ¿Desea agregar parte a otro coche? (s/n): ");
            } catch (MatriculaInvalidaException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                boolean intentarDeNuevo = confirmarAccion(" ¿Desea intentar nuevamente esta acción? (s/n):");
                if (!intentarDeNuevo) {
                    agregarParte = false;
                }

            }
        }
    }

    private void agregarNuevaPartesACoches(Coche coche) {
        boolean agregarOtroParte = true;

        while (agregarOtroParte) {
            try {
                String codigo = leerEntrada(" Introduzca un nuevo código de la parte: ");
                ValidacionUtil.validarEntradaCodigo(codigo);

                String descripcion = leerEntrada(" Introduzca la descriptión del servicio: ");
                ValidacionUtil.validarEntradaDescripcion(descripcion);

                String importe = leerEntrada(" Introduzca el importe del servicio: ");
                ValidacionUtil.validarEntradaImporte(importe);

                Parte parteNuevo = new Parte(codigo, descripcion, importe);
                gestionParte.crearParte(parteNuevo);

                gestionTaller.asignarPartesACoche(coche.matricula(), parteNuevo.codigo());

                System.out.println(" \n Parte associada correctamente al coche " + coche.matricula());

                agregarOtroParte = confirmarAccion(" \n ¿Desea agregar otra parte al mismo coche? (s/n): ");

                if (!agregarOtroParte) {
                    return;
                }
            } catch (CodigoInvalidoException | ImporteInvalidoException e) {
                System.out.println(" Error: " + e.getMessage());
                boolean intentarDeNuevo = confirmarAccion(
                        " ¿Desea intentar agregar esta parte nuevamente? (s/n):");
                if (!intentarDeNuevo) {
                    return;
                }
            }
        }
    }

    private void eliminarPartePorCoche() {
        Matricula matricula = leerYValidarMatricula(" Introduzca la matricula del coche: ");
        Coche coche = gestionCoche.buscar(matricula);
        gestionTaller.mostrarPartesDeCoche(coche.matricula());

        String codigo = leerYValidarCodigo(" Introduzca el código de la parte a eliminar: ");
        if (confirmarAccion(
                " \n ¿Desea eliminar la parte del coche " + coche.matricula() + "?" + "(s/n)")) {
            gestionTaller.eliminarParteDeCoche(codigo, matricula);
            System.out.println(" \n Parte eliminada correctamente!");
        }

    }

    private void eliminarParteGlobal() {
        String codigo = leerYValidarCodigo(" Introduzca el código de la parte a eliminar globalmente: ");
        if (confirmarAccion(" ¿Desea eliminar la parte con código " + codigo + "?" + "(s/n)")) {
            gestionTaller.eliminarParteGlobal(codigo);
            System.out.println(" Parte eliminada globalmente.");
        }
    }

    private Matricula leerYValidarMatricula(String mensaje) {
        String entrada = leerEntrada(mensaje);
        ValidacionUtil.validarEntradaMatricula(entrada);
        return ValidacionUtil.parsearMatricula(entrada);
    }

    private String leerYValidarCodigo(String mensaje) {
        String codigo = leerEntrada(mensaje);
        ValidacionUtil.validarEntradaCodigo(codigo);
        return codigo;
    }

    private boolean confirmarAccion(String mensaje) {
        while (true) {
            String respuesta = leerEntrada(mensaje);
            if (respuesta.equalsIgnoreCase("s")) return true;
            if (respuesta.equalsIgnoreCase("n")) return false;
            System.out.println(" Opción inválida! Por favor, ingrese 's' o 'n'.");

        }
    }

    private String leerEntrada(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }
    public void cerrar() {
        if (entrada != null) {
            entrada.close();
        }
    }

}
