package com.pragma.microservicioplazoleta.domain.usercase;

import com.pragma.microservicioplazoleta.domain.api.IPedidoServicio;
import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoUseCase implements IPedidoServicio {
    private final IPedidoRepositorio pedidoRepositorio;

    public PedidoUseCase(IPedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        if (pedidoRepositorio.clienteTienePedidoEnProceso(pedido.getIdCliente())) {
            throw new IllegalArgumentException("El cliente ya tiene un pedido en proceso");
        }

        List<Long> idPlatos = pedido.getPlatos().stream()
                .map(PedidoPlato::getIdPlato)
                .toList();

        if (!pedidoRepositorio.platosPerteneceARestaurante(idPlatos, pedido.getIdRestaurante())) {
            throw new IllegalArgumentException("Todos los platos deben pertenecer al mismo restaurante");
        }

        pedido.setEstado("Pendiente");
        pedido.setFecha(LocalDateTime.now());
        return pedidoRepositorio.guardarPedido(pedido);
    }

    @Override
    public List<Pedido> listarPedidos(Long idRestaurante, String estado, int pagina, int tamano) {
        return pedidoRepositorio.listarPedidosPorRestauranteYEstado(idRestaurante, estado, pagina, tamano);
    }

    @Override
    public Pedido asignarEmpleado(Long idPedido, Long idEmpleado, Long idRestauranteEmpleado) {
        Pedido pedido = pedidoRepositorio.obtenerPedidoPorId(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("El pedido no existe"));

        if (!pedidoRepositorio.pedidoPerteneceARestaurante(idPedido, idRestauranteEmpleado)) {
            throw new IllegalArgumentException("El pedido no pertenece al restaurante del empleado");
        }
        pedido.setEstado("En_Preparacion");
        pedido.setIdChef(idEmpleado);
        return pedidoRepositorio.pedidoEnPreparacion(pedido);
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(Long idCliente) {
        return pedidoRepositorio.listarPedidosPorCliente(idCliente);
    }
}
