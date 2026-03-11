package com.pragma.microservicioplazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class RestauranteEmpleado {
    private Long id;
    private Long idEmpleado;
    private Long idRestaurante;
}
