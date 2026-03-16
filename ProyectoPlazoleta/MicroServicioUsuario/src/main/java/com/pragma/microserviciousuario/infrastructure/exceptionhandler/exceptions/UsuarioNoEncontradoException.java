package com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String mensaje) { super(mensaje); }
}