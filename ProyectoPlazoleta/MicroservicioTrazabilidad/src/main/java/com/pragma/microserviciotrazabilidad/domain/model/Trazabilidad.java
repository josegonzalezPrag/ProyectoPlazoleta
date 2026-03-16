package com.pragma.microserviciotrazabilidad.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @Builder
public class Trazabilidad {
    private String id;
    private Long idPedido;
    private Long idCliente;
    private Long idEmpleado;
    private Long idRestaurante;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
