package com.pragma.microservicioplazoleta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    private Long id;
    private String correo;
    private String rolNombre;
}
