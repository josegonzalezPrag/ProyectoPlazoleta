package com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions;

public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String mensaje) { super(mensaje); }
}