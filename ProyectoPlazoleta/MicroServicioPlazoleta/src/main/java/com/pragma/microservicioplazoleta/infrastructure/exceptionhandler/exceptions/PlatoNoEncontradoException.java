package com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions;

public class PlatoNoEncontradoException extends RuntimeException {
    public PlatoNoEncontradoException(String mensaje) { super(mensaje); }
}
