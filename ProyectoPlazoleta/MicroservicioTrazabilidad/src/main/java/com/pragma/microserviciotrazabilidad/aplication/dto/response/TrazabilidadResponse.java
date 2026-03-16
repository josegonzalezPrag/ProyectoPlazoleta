package com.pragma.microserviciotrazabilidad.aplication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TrazabilidadResponse {
    private String id;
    private Long idPedido;
    private Long idCliente;
    private Long idEmpleado;
    private Long idRestaurante;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
