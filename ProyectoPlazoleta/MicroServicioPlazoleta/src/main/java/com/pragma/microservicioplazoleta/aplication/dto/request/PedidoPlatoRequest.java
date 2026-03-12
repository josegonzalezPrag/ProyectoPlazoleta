package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPlatoRequest {
    @NotNull
    private Long idPlato;
    @NotNull
    @Positive
    private Integer cantidad;
}
