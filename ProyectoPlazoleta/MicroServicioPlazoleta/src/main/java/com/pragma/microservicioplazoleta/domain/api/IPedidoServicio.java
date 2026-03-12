package com.pragma.microservicioplazoleta.domain.api;

import com.pragma.microservicioplazoleta.domain.model.Pedido;

public interface IPedidoServicio {
    Pedido crearPedido(Pedido pedido);
}
