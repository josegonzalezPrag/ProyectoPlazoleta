package com.pragma.microservicioplazoleta.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
public class Pedido {
    private Long id;
    private Long idCliente;
    private LocalDateTime fecha;
    private String estado;
    private Long idChef;
    private Long idRestaurante;
    private List<PedidoPlato> platos;
}
