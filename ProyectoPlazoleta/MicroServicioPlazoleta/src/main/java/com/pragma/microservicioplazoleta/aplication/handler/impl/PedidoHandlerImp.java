package com.pragma.microservicioplazoleta.aplication.handler.impl;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPedidoHandler;
import com.pragma.microservicioplazoleta.aplication.mapper.PedidoRequestMapper;
import com.pragma.microservicioplazoleta.domain.api.IPedidoServicio;
import com.pragma.microservicioplazoleta.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoHandlerImp implements IPedidoHandler {
    private final IPedidoServicio pedidoServicio;
    private final PedidoRequestMapper pedidoRequestMapper;

    @Override
    public PedidoResponse crearPedido(PedidoRequest request, Long idCliente) {
        Pedido pedido = pedidoRequestMapper.toPedido(request);
        pedido.setIdCliente(idCliente);
        return pedidoRequestMapper.toResponse(pedidoServicio.crearPedido(pedido));
    }

    @Override
    public List<PedidoResponse> listarPedidos(Long idRestaurante, String estado, int pagina, int tamano) {
        return pedidoServicio.listarPedidos(idRestaurante, estado, pagina, tamano)
                .stream()
                .map(pedidoRequestMapper::toResponse)
                .toList();
    }
}
