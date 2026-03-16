package com.pragma.microservicioplazoleta.aplication.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class TiempoEmpleadoResponse {
    private Long idEmpleado;
    private double tiempoMedioMinutos;
}
