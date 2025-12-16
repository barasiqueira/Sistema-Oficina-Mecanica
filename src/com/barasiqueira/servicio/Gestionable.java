package com.barasiqueira.servicio;

import com.barasiqueira.modelo.Matricula;

public interface Gestionable<T> {
    void crear(T elemento);
    T buscar(Matricula id);
    void listar();
    void eliminar(String id);
}
