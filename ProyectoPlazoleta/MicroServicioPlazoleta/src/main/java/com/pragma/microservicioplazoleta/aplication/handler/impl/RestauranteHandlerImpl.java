package com.pragma.microservicioplazoleta.aplication.handler.impl;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteHandler;
import com.pragma.microservicioplazoleta.aplication.mapper.RestaurantRequestMapper;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteHandlerImpl implements IRestauranteHandler {
    private final IRestauranteServicio restauranteServicio;
    private final RestaurantRequestMapper restauranteRequestMapper;

    @Override
    public RestauranteResponse crearRestaurante(RestauranteRequest request) {
        var restaurante = restauranteRequestMapper.toRestaurante(request);
        var guardado = restauranteServicio.crearRestaurante(restaurante);
        return restauranteRequestMapper.toResponse(guardado);
    }
}
