package com.pragma.microservicioplazoleta.aplication.handler;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;

public interface IRestauranteHandler {
    RestauranteResponse crearRestaurante(RestauranteRequest request);


}
