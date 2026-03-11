package com.pragma.microservicioplazoleta.aplication.handler.impl;


import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoEstadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.CategoriaResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPlatoHandler;
import com.pragma.microservicioplazoleta.aplication.mapper.PlatoRequestMapper;
import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlatoHandlerImpl implements IPlatoHandler {
    private final IPlatoServicio platoServicio;
    private final PlatoRequestMapper platoRequestMapper;

    @Override
    public PlatoResponse crearPlato(PlatoRequest request) {
        var plato = platoRequestMapper.toPlato(request);
        var guardado = platoServicio.crearPlato(plato);
        PlatoResponse response = platoRequestMapper.toResponse(guardado);

        if (guardado.getCategoria() != null) {
            CategoriaResponse categoriaResponse = new CategoriaResponse();
            categoriaResponse.setId(guardado.getCategoria().getId());
            categoriaResponse.setNombre(guardado.getCategoria().getNombre());
            categoriaResponse.setDescripcion(guardado.getCategoria().getDescripcion());
            response.setIdcategoria(categoriaResponse);
        }
        return response;
    }

    @Override
    public PlatoResponse actualizarPlato(Long id, PlatoUptadeRequest request) {
        var actualizado = platoServicio.actualizarPlato(id, request.getPrecio(), request.getDescripcion());
        return platoRequestMapper.toResponse(actualizado);
    }

    @Override
    public PlatoResponse cambiarEstadoPlato(Long id, PlatoEstadoRequest request, Long idPropietario) {
        var actualizado = platoServicio.cambiarEstadoPlato(id, request.getActivo(), idPropietario);
        return platoRequestMapper.toResponse(actualizado);
    }

    @Override
    public List<PlatoResponse> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano) {
        return platoServicio.listarPlatosPorRestaurante(idRestaurante, idCategoria, pagina, tamano)
                .stream()
                .map(plato -> {
                    PlatoResponse response = platoRequestMapper.toResponse(plato);
                    if (plato.getCategoria() != null) {
                        CategoriaResponse categoriaResponse = new CategoriaResponse();
                        categoriaResponse.setId(plato.getCategoria().getId());
                        categoriaResponse.setNombre(plato.getCategoria().getNombre());
                        categoriaResponse.setDescripcion(plato.getCategoria().getDescripcion());
                        response.setIdcategoria(categoriaResponse);
                    }
                    return response;
                })
                .toList();
    }

}
