package com.pragma.microserviciomensajeria.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MensajeRequet {
    @NotBlank
    private String numeroCelular;
    @NotBlank
    private String mensaje;
}
