package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;

import java.util.List;

public interface ITrazabilidadClient {
    void registrarCambioEstado(TrazabilidadRequest request);
    List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido);
    List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante);
    List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante);
}
