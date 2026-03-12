package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PedidoRequest {
    @NotNull
    private Long idRestaurante;

    @NotNull
    @Size(min = 1)
    private List<PedidoPlatoRequest> platos;
}
