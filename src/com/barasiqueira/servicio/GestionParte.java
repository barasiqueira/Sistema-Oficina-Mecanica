package com.barasiqueira.servicio;

import com.barasiqueira.modelo.Parte;
import com.barasiqueira.util.ValidacionUtil;

import java.util.HashMap;
import java.util.Map;

public class GestionParte {

    private final Map<String, Parte> partes = new HashMap<>();

    public Map<String, Parte> getPartes() {
        return partes;
    }

    public void crearParte(Parte parte) {
        ValidacionUtil.validadorGenerico(parte, "La parte");

        if (hayAlgunParteCadastrado(parte)) {
            throw new IllegalArgumentException("Ya existe una parte con ese matricula." + parte.codigo());
        }

        partes.put(parte.codigo(), parte);
    }

    private boolean hayAlgunParteCadastrado(Parte parte) {
        return partes.containsKey(parte.codigo());
    }
}
