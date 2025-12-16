package com.barasiqueira.servicio;

import com.barasiqueira.modelo.Coche;
import com.barasiqueira.modelo.Matricula;
import com.barasiqueira.util.ValidacionUtil;

import java.util.HashMap;
import java.util.Map;


public class GestionCoche implements Gestionable<Coche> {

    private final Map<Matricula, Coche> coches = new HashMap<>();

    @Override
    public void crear(Coche coche) {
        ValidacionUtil.validadorGenerico(coche, " El Coche");

        if (hayAlgunCocheCadastrado(coche)) {
            throw new IllegalArgumentException(" Ya existe un coche con esa matrícula: " + coche.matricula());
        }
        coches.put(coche.matricula(), coche);
    }

    @Override
    public Coche buscar(Matricula matricula) {
        ValidacionUtil.validadorGenerico(matricula, "La matrícula");

        Coche coche = coches.get(matricula);
        if (coche == null) {
            throw new IllegalArgumentException(" No se encontró ningún coche con la matrícula: " + matricula);
        }

        System.out.printf(" \nCoche con matrícula %s encontrada!", coche.matricula());
        return coche;
    }

    @Override
    public void listar() {
        if (coches.isEmpty()) {
            System.out.println(" No hay coches registrados.");
            return;
        }

        System.out.println(" Listado de coches registrados:");
        for (Map.Entry<Matricula, Coche> entrada : coches.entrySet()) {
            Matricula matricula = entrada.getKey();
            System.out.println(matricula);
        }
    }

    @Override
    public void eliminar(String matricula) {
        Matricula matriculaValida = ValidacionUtil.parsearMatricula(matricula);

        if (!hayAlgunCocheCadastrado(matriculaValida)) {
            throw new IllegalArgumentException(" No exite un coche con la matrícula: " + matriculaValida);
        }
        coches.remove(matriculaValida);
    }

    private boolean hayAlgunCocheCadastrado(Coche coche) {
        return coches.containsKey(coche.matricula());
    }

    private boolean hayAlgunCocheCadastrado(Matricula matricula) {
        return coches.containsKey(matricula);
    }

}


