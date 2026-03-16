package com.pragma.microservicioplazoleta.aplication.handler.impl;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteHandler;
import com.pragma.microservicioplazoleta.aplication.mapper.RestaurantRequestMapper;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestauranteHandlerImpl implements IRestauranteHandler {
    private final IRestauranteServicio restauranteServicio;
    private final RestaurantRequestMapper restauranteRequestMapper;

    @Override
    public RestauranteResponse crearRestaurante(RestauranteRequest request) {
        Restaurante restaurante = restauranteRequestMapper.toRestaurante(request);
        Restaurante guardado = restauranteServicio.crearRestaurante(restaurante);
        return restauranteRequestMapper.toResponse(guardado);
    }

    @Override
    public List<RestauranteResponse> listarRestaurantes(int pagina, int tamano) {
        return restauranteServicio.listarRestaurantes(pagina, tamano)
                .stream()
                .map(restauranteRequestMapper::toResponse)
                .toList();
    }
}
