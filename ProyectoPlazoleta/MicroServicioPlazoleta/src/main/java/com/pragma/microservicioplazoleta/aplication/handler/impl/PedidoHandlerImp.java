package com.pragma.microservicioplazoleta.aplication.handler.impl;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;
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
    public List<PedidoResponse> listarPedidos(Long idEmpleado, String estado, int pagina, int tamano) {
        return pedidoServicio.listarPedidos(idEmpleado, estado, pagina, tamano)
                .stream()
                .map(pedidoRequestMapper::toResponse)
                .toList();
    }

    @Override
    public PedidoResponse asignarEmpleado(Long idPedido, Long idEmpleado) {
        return pedidoRequestMapper.toResponse(pedidoServicio.asignarEmpleado(idPedido, idEmpleado));
    }

    @Override
    public List<PedidoResponse> listarPedidosPorCliente(Long idCliente) {
        return pedidoServicio.listarPedidosPorCliente(idCliente)
                .stream()
                .map(pedidoRequestMapper::toResponse)
                .toList();
    }

    @Override
    public PedidoResponse marcarComoListo(Long idPedido, Long idChef) {
        return pedidoRequestMapper.toResponse(pedidoServicio.marcarComoListo(idPedido, idChef));
    }

    @Override
    public PedidoResponse entregarPedido(Long idPedido, String codigoEntrega, Long idEmpleado) {
        return pedidoRequestMapper.toResponse(pedidoServicio.entregarPedido(idPedido, codigoEntrega, idEmpleado));
    }

    @Override
    public PedidoResponse cancelarPedido(Long idPedido, Long idCliente) {
        return pedidoRequestMapper.toResponse(pedidoServicio.cancelarPedido(idPedido, idCliente));
    }

    @Override
    public List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido, Long idCliente) {
        return pedidoServicio.obtenerTrazabilidadPedido(idPedido, idCliente);
    }
}
