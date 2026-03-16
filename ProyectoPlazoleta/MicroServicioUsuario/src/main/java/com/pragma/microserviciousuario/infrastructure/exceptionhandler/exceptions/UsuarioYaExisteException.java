package com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions;

public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String mensaje) { super(mensaje); }
}