package com.pragma.microservicioplazoleta.aplication.handler;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;

public interface IPedidoHandler {
    PedidoResponse crearPedido(PedidoRequest request, Long idCliente);
}
