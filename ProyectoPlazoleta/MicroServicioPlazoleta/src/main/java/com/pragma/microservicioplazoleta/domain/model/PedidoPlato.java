package com.pragma.microservicioplazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class PedidoPlato {
    private Long idPedido;
    private Long idPlato;
    private Integer cantidad;
}
