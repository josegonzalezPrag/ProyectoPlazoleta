package com.pragma.microservicioplazoleta.aplication.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class PlatoResponse {
    private Long id;
    private String nombre;
    private Long precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private Boolean activo;
    private Long idRestaurante;
}
