package com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones;

public class PedidoNoEntregadoException extends RuntimeException {
    public PedidoNoEntregadoException(String mensaje) { super(mensaje); }
}