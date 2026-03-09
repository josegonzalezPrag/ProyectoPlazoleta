package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlatoUptadeRequest {
    @NotNull
    @Positive
    private Long precio;

    @NotBlank
    private String descripcion;

}