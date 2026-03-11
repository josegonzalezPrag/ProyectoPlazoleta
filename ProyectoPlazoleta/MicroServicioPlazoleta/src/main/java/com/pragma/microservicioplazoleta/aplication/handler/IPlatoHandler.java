package com.pragma.microservicioplazoleta.aplication.handler;


import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoEstadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;

import java.util.List;

public interface IPlatoHandler {
    PlatoResponse crearPlato(PlatoRequest request);
    PlatoResponse actualizarPlato(Long id, PlatoUptadeRequest request);
    PlatoResponse cambiarEstadoPlato(Long id, PlatoEstadoRequest request, Long idPropietario);
    List<PlatoResponse> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano);
}
