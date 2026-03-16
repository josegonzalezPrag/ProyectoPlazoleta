package com.pragma.microserviciotrazabilidad.aplication.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class TiempoPedidoResponse {
    private Long idPedido;
    private long minutosEntrega;
}
