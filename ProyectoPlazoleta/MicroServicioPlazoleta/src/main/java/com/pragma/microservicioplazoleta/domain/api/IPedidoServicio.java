package com.pragma.microservicioplazoleta.domain.api;

import com.pragma.microservicioplazoleta.domain.model.Pedido;

import java.util.List;

public interface IPedidoServicio {
    Pedido crearPedido(Pedido pedido);
    List<Pedido> listarPedidos(Long idRestaurante, String estado, int pagina, int tamano);
}
