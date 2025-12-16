package com.barasiqueira.App;


import com.barasiqueira.ui.Consola;

public class AppTallerMecanico {

    public static void main(String[] args) {
        Consola consola = new Consola();
        boolean continuar = true;

        try {
            while (continuar) {
                try {
                    String option = consola.leerOpcionMenu();
                    continuar = consola.ejecutarMenu(option);
                } catch (Exception e) {
                    System.out.println(" Error: " + e.getMessage());
                }
            }
        } finally {
            consola.cerrar();
        }

    }

}
