package com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions;

public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String mensaje) { super(mensaje); }
}