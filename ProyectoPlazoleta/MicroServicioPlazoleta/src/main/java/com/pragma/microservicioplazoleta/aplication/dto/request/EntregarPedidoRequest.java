package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntregarPedidoRequest {
    @NotBlank
    private String codigoEntrega;
}
