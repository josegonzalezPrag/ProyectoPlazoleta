package com.pragma.microservicioplazoleta.aplication.handler.impl;


import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPlatoHandler;
import com.pragma.microservicioplazoleta.aplication.mapper.PlatoRequestMapper;
import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandlerImpl implements IPlatoHandler {
    private final IPlatoServicio platoServicio;
    private final PlatoRequestMapper platoRequestMapper;

    @Override
    public PlatoResponse crearPlato(PlatoRequest request) {
        var plato = platoRequestMapper.toPlato(request);
        var guardado = platoServicio.crearPlato(plato);
        return platoRequestMapper.toResponse(guardado);
    }

    @Override
    public PlatoResponse actualizarPlato(Long id, PlatoUptadeRequest request) {
        var actualizado = platoServicio.actualizarPlato(id, request.getPrecio(), request.getDescripcion());
        return platoRequestMapper.toResponse(actualizado);
    }

}
