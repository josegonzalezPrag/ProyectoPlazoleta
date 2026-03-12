package com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedidos_platos")
@Getter
@Setter
public class PedidoPlatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idPlato;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;
}
