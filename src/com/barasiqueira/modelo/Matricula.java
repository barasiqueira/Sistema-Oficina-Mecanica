package com.barasiqueira.modelo;

public record Matricula(String provincia, String numero, String letra) {

    @Override
    public String toString() {
        return provincia + "-" + numero + "-" + letra;
    }
}
