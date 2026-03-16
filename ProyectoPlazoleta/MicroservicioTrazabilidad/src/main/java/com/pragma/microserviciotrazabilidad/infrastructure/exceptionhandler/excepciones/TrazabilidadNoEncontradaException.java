package com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones;

public class TrazabilidadNoEncontradaException  extends RuntimeException {
    public TrazabilidadNoEncontradaException(String mensaje) { super(mensaje); }
}
