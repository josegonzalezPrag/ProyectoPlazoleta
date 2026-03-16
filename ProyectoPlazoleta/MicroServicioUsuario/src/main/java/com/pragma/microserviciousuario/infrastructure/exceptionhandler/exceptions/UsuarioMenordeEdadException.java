package com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions;

public class UsuarioMenordeEdadException extends RuntimeException {
    public UsuarioMenordeEdadException(String mensaje) { super(mensaje); }
}
