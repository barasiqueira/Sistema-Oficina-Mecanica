package com.barasiqueira.modelo;

public record Coche(Matricula matricula) {

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Coche coche = (Coche) object;
        return matricula.equals(coche.matricula);
    }

    @Override
    public int hashCode() {
        return matricula.hashCode();
    }
}
