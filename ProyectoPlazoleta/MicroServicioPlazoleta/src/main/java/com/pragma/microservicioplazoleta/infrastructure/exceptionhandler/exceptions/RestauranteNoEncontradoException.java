package com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions;

public class RestauranteNoEncontradoException extends RuntimeException {
    public RestauranteNoEncontradoException(String mensaje) { super(mensaje); }
}