package com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "trazabilidad")
@Getter @Setter @Builder
public class TrazabilidadEntity {
    @Id
    private String id;
    private Long idPedido;
    private Long idCliente;
    private Long idEmpleado;
    private Long idRestaurante;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
