package com.pragma.microservicioplazoleta.domain.model;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurante {
    private Long id;
    private String nombre;
    private Integer nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idPropietario;
}
