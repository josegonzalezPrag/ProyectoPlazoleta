package com.pragma.microserviciotrazabilidad.aplication.handler.impl;

import com.pragma.microserviciotrazabilidad.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microserviciotrazabilidad.aplication.handler.ITrazabilidadHandler;
import com.pragma.microserviciotrazabilidad.aplication.mapper.TrazabilidadResquestMapper;
import com.pragma.microserviciotrazabilidad.domain.api.ITrazabilidadServicio;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrazabilidadHandlerImpl implements ITrazabilidadHandler {
    private final ITrazabilidadServicio servicio;
    private final TrazabilidadResquestMapper mapper;

    @Override
    public TrazabilidadResponse registrarCambioEstado(TrazabilidadRequest request) {
        Trazabilidad trazabilidad = mapper.toModel(request);
        return mapper.toResponse(servicio.registrarCambioEstado(trazabilidad));
    }

    @Override
    public List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido) {
        return servicio.obtenerTrazabilidadPedido(idPedido)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public long calcularTiempoEntrega(Long idPedido) {
        return servicio.calcularTiempoEntrega(idPedido);
    }
}
