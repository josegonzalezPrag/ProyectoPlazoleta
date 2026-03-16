package com.pragma.microservicioplazoleta.aplication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TrazabilidadRequest {
    private Long idPedido;
    private Long idCliente;
    private Long idEmpleado;
    private Long idRestaurante;
    private String estadoAnterior;
    private String estadoNuevo;
}
