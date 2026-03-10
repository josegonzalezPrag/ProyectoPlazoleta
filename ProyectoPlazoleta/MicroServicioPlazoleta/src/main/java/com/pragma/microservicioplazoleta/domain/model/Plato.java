package com.pragma.microservicioplazoleta.domain.model;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plato {
    private Long id;
    private String nombre;
    private Long precio;
    private String descripcion;
    private String urlImagen;
    private Categoria categoria;
    private Boolean activo;
    private Long idRestaurante;
}