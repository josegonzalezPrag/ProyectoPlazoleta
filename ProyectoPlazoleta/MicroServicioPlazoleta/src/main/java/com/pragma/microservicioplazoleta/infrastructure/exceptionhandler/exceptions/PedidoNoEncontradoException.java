package com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(String mensaje) { super(mensaje); }
}
