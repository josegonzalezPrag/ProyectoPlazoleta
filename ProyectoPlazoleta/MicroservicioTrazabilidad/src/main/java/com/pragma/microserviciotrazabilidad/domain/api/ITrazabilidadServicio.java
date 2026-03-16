package com.pragma.microserviciotrazabilidad.domain.api;

import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import java.util.List;

public interface ITrazabilidadServicio {
    Trazabilidad registrarCambioEstado(Trazabilidad trazabilidad);
    List<Trazabilidad> obtenerTrazabilidadPedido(Long idPedido);
    List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante);
    List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante);
    long calcularTiempoEntrega(Long idPedido);
}
