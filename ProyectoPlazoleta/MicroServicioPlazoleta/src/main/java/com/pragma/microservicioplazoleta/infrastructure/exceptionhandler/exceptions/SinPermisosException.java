package com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions;

public class SinPermisosException extends RuntimeException {
    public SinPermisosException(String mensaje) { super(mensaje); }
}
