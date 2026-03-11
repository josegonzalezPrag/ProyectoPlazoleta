package com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurante_empleado")
@Getter
@Setter
public class RestauranteEmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idEmpleado;
    private Long idRestaurante;

}
