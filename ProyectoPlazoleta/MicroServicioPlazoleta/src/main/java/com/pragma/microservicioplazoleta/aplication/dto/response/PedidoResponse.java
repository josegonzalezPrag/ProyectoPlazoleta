package com.pragma.microservicioplazoleta.aplication.dto.response;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoPlatoRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PedidoResponse {
    private Long id;
    private Long idCliente;
    private LocalDateTime fecha;
    private String estado;
    private Long idRestaurante;
    private List<PedidoPlatoRequest> platos;
}
