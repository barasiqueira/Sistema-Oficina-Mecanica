package com.barasiqueira.modelo;

public record Parte(String codigo, String descripcion, String importe) {

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Parte parte = (Parte) object;
        return codigo.equals(parte.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}
