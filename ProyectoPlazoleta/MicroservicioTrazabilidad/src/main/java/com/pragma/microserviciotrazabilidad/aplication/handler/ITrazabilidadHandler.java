package com.pragma.microserviciotrazabilidad.aplication.handler;

import com.pragma.microserviciotrazabilidad.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TrazabilidadResponse;
import java.util.List;

public interface ITrazabilidadHandler {
    TrazabilidadResponse registrarCambioEstado(TrazabilidadRequest request);
    List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido);
    long calcularTiempoEntrega(Long idPedido);
}
