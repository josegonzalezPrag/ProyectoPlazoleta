package com.pragma.microservicioplazoleta.aplication.handler;

import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteEmpleadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteEmpleadoResponse;

public interface IRestauranteEmpleadoHandler {
    RestauranteEmpleadoResponse asignarEmpleado(RestauranteEmpleadoRequest request);
}
