package com.pragma.microservicioplazoleta.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {
    @NotBlank
    private String nombre;

    @NotNull
    @Positive
    private Integer nit;

    @NotBlank
    private String direccion;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{1,12}$", message = "Teléfono inválido, máximo 13 caracteres")
    private String telefono;

    @NotBlank
    private String urlLogo;

    @NotNull
    private Long idPropietario;

}
