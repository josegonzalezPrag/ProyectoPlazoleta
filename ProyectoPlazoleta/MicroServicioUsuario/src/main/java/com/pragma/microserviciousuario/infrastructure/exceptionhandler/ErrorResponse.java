package com.pragma.microserviciousuario.infrastructure.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int codigo;
    private String mensaje;

}
