package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlatoEstadoRequest {
    @NotNull
    private Boolean activo;

}
