package com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class RestaurabteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idPropietario;
}
