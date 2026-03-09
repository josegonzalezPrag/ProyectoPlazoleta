package com.pragma.microservicioplazoleta.aplication.handler;


import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;

public interface IPlatoHandler {
    PlatoResponse crearPlato(PlatoRequest request);
    PlatoResponse actualizarPlato(Long id, PlatoUptadeRequest request);
}
