package com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones;

public class PedidoCanceladoException extends RuntimeException {
    public PedidoCanceladoException(String mensaje) { super(mensaje); }
}