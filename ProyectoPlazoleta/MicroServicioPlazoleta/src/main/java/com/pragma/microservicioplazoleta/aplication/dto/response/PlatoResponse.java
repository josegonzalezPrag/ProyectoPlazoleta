package com.pragma.microservicioplazoleta.aplication.dto.response;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.CategoriaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class PlatoResponse {
    private Long id;
    private String nombre;
    private Long precio;
    private String descripcion;
    private String urlImagen;
    private CategoriaEntity idcategoria;
    private Boolean activo;
    private Long idRestaurante;
}
