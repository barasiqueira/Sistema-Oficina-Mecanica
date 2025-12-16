package com.barasiqueira.servicio;

import com.barasiqueira.modelo.Coche;
import com.barasiqueira.modelo.Matricula;
import com.barasiqueira.modelo.Parte;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GestionTaller {

    private final Map<Coche, Set<Parte>> cocheDeParte = new HashMap<>();
    private final GestionCoche gestionCoche;
    private final GestionParte gestionParte;

    public GestionTaller(GestionCoche gestionCoche, GestionParte gestionParte) {
        this.gestionCoche = gestionCoche;
        this.gestionParte = gestionParte;
    }

    public void asignarPartesACoche(Matricula matricula, String codigo) {
        Coche coche = gestionCoche.buscar(matricula);

        if (coche == null) {
            throw new IllegalArgumentException(" No existe un coche con ese matricula" + matricula);
        }

        Parte parte = gestionParte.getPartes().get(codigo);
        if (parte == null) {
            throw new IllegalArgumentException(" No existe un coche con ese parte: " + codigo);
        }

        Set<Parte> partesAssociadas = cocheDeParte.get(coche);
        if (partesAssociadas == null) {
            partesAssociadas = new HashSet<>();
            cocheDeParte.put(coche, partesAssociadas);
        }
        partesAssociadas.add(parte);
    }

    public void listarTodosCochesYPartes() {

        if (cocheDeParte.isEmpty()) {
            System.out.println(" No hay coches registrados.");
            return;
        }

        for (Map.Entry<Coche, Set<Parte>> entrada : cocheDeParte.entrySet()) {
            Coche coche = entrada.getKey();
            Set<Parte> partes = entrada.getValue();

            System.out.println(" \n Coche: " + coche.matricula());
            System.out.println(" ======================");

            if (partes == null || partes.isEmpty()) {
                System.out.println(" No hay partes asociadas.");
            } else {
                imprimirPartes(partes);
            }
        }
    }

    public void mostrarPartesDeCoche(Matricula matricula) {
        Set<Parte> partesAsociadas = obtenerPartesDeCochePorMatricula(matricula);

        if (partesAsociadas.isEmpty()) {
            System.out.println(" El coche con matrícula " + matricula + " no tiene partes asociadas.");
            return;
        }

        System.out.println(" \n Partes asociadas al coche " + matricula + ":");
        imprimirPartes(partesAsociadas);
    }

    public void eliminarParteDeCoche(String codigo, Matricula matricula) {
        Set<Parte> partesAsociadas = obtenerPartesDeCochePorMatricula(matricula);

        Parte parteEliminar = null;
        for (Parte p : partesAsociadas) {
            if (p.codigo().equals(codigo)) {
                parteEliminar = p;
                break;
            }
        }

        if (parteEliminar == null) {
            System.out.println(" La parte con código " + codigo + " no está asociadas al coche " + matricula);
            return;
        }

        partesAsociadas.remove(parteEliminar);
        System.out.println(" Parte " + codigo + " eliminada correctamente del coche " + matricula);
    }

    public void eliminarParteGlobal(String codigo) {
        Parte parte = gestionParte.getPartes().remove(codigo);

        if (parte == null) {
            System.out.println(" No existe una parte con ese codigo." + codigo);
            return;
        }

        for (Set<Parte> partesAsociadas : cocheDeParte.values()) {
            partesAsociadas.remove(parte);
        }

        System.out.println(" Parte " + codigo + " eliminada globalmente. ");
    }

    private Set<Parte> obtenerPartesDeCochePorMatricula(Matricula matricula) {
        Coche coche = gestionCoche.buscar(matricula);

        if (coche == null) {
            throw new IllegalArgumentException(" No existe un coche con la matricula");
        }
        Set<Parte> partesAsociadas = cocheDeParte.get(coche);

        if (partesAsociadas == null || partesAsociadas.isEmpty()) {
            System.out.printf(" El coche con matrícula " + matricula + " no tiene partes asociadas.");
            return new HashSet<>();
        }

        return partesAsociadas;
    }

    private static void imprimirPartes(Set<Parte> partes) {
        for (Parte parte : partes) {
            System.out.printf(" Parte %s -> %s, %s €%n",
                    parte.codigo(),
                    parte.descripcion(),
                    parte.importe());
        }
    }

}
