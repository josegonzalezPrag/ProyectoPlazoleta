package com.pragma.microservicioplazoleta.domain.api;

import com.pragma.microservicioplazoleta.domain.model.Pedido;

import java.util.List;

public interface IPedidoServicio {
    Pedido crearPedido(Pedido pedido);
    Pedido marcarComoListo(Long idPedido, Long idChef);
    Pedido asignarEmpleado(Long idPedido, Long idEmpleado);
    Pedido cancelarPedido(Long idPedido, Long idCliente);
    Pedido entregarPedido(Long idPedido, String codigoEntrega, Long idEmpleado);
    List<Pedido> listarPedidos(Long idEmpleado, String estado, int pagina, int tamano);
    List<Pedido> listarPedidosPorCliente(Long idCliente);
}
