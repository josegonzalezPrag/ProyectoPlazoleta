package com.pragma.microserviciousuario.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestauranteEmpleadoRequest {
    private Long idEmpleado;
    private Long idRestaurante;
}
