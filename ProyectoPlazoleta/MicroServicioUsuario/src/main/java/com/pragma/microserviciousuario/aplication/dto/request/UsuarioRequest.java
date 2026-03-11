package com.pragma.microserviciousuario.aplication.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UsuarioRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    @Positive
    private Long documentoIdentidad;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{1,12}$", message = "Celular inválido, máximo 13 caracteres y puede iniciar con +")
    private String celular;

    @NotNull
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotBlank
    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank
    private String clave;

    private Long idRestaurante;
}
