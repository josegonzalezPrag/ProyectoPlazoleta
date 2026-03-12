package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoRepositorio {
    Pedido guardarPedido(Pedido pedido);
    Optional<Pedido> obtenerPedidoPorId(Long id);
    boolean clienteTienePedidoEnProceso(Long idCliente);
    boolean platosPerteneceARestaurante(List<Long> idPlatos, Long idRestaurante);
}
