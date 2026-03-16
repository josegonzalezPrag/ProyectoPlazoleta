package com.pragma.microservicioplazoleta.aplication.handler;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;

import java.util.List;

public interface IPedidoHandler {
    PedidoResponse crearPedido(PedidoRequest request, Long idCliente);
    List<PedidoResponse> listarPedidos(Long idEmpleado, String estado, int pagina, int tamano);
    PedidoResponse asignarEmpleado(Long idPedido, Long idEmpleado);
    PedidoResponse marcarComoListo(Long idPedido, Long idChef);
    PedidoResponse cancelarPedido(Long idPedido, Long idCliente);
    PedidoResponse entregarPedido(Long idPedido, String codigoEntrega, Long idEmpleado);
    List<PedidoResponse> listarPedidosPorCliente(Long idCliente);
    List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido, Long idCliente);
}
