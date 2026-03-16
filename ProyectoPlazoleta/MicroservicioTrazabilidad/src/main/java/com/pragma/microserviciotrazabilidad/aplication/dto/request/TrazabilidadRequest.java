package com.pragma.microserviciotrazabilidad.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TrazabilidadRequest {
    @NotNull
    private Long idPedido;
    @NotNull
    private Long idCliente;
    private Long idEmpleado;
    @NotNull
    private Long idRestaurante;
    private String estadoAnterior;
    @NotBlank
    private String estadoNuevo;
}
