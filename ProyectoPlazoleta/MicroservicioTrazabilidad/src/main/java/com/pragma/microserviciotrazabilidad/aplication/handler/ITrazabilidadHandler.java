package com.pragma.microserviciotrazabilidad.aplication.handler;

import com.pragma.microserviciotrazabilidad.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TrazabilidadResponse;
import java.util.List;

public interface ITrazabilidadHandler {
    TrazabilidadResponse registrarCambioEstado(TrazabilidadRequest request);
    List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido);
    List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante);
    List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante);
    long calcularTiempoEntrega(Long idPedido);
}
