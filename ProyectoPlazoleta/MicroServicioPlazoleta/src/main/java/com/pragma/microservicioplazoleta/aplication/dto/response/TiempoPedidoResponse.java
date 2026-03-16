package com.pragma.microservicioplazoleta.aplication.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TiempoPedidoResponse {
    private Long idPedido;
    private long minutosEntrega;
}
