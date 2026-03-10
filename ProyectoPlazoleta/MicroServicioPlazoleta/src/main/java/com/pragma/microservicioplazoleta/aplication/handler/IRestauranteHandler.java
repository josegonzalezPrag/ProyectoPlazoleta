package com.pragma.microservicioplazoleta.aplication.handler;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;

import java.util.List;

public interface IRestauranteHandler {
    RestauranteResponse crearRestaurante(RestauranteRequest request);
    List<RestauranteResponse> listarRestaurantes(int pagina, int tamano);

}
