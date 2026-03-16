package com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions;

public class CredencialesInvalidoException extends RuntimeException {
    public CredencialesInvalidoException(String mensaje) { super(mensaje); }
}
