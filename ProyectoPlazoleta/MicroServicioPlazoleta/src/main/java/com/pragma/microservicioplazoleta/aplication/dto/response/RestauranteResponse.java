package com.pragma.microservicioplazoleta.aplication.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteResponse {
    private Long id;
    private String nombre;
    private Integer nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idPropietario;
}
