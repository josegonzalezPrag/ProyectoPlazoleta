package com.pragma.microservicioplazoleta.aplication.handler;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;

import java.util.List;

public interface IPedidoHandler {
    PedidoResponse crearPedido(PedidoRequest request, Long idCliente);
    List<PedidoResponse> listarPedidos(Long idRestaurante, String estado, int pagina, int tamano);
    PedidoResponse asignarEmpleado(Long idPedido, Long idEmpleado, Long idRestauranteEmpleado);
    List<PedidoResponse> listarPedidosPorCliente(Long idCliente);
}
