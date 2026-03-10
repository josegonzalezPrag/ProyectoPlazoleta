package com.pragma.microserviciousuario.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rol {
    private Long id;
    private String nombre;
    private String descripcion;
}
