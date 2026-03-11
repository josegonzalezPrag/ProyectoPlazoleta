package com.pragma.microservicioplazoleta.aplication.handler.impl;

import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteEmpleadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteEmpleadoHandler;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteEmpleadoServicio;
import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteEmpleadoHandlerImpl implements IRestauranteEmpleadoHandler {
    private final IRestauranteEmpleadoServicio servicio;

    @Override
    public RestauranteEmpleadoResponse asignarEmpleado(RestauranteEmpleadoRequest request) {
        RestauranteEmpleado restauranteEmpleado = RestauranteEmpleado.builder()
                .idEmpleado(request.getIdEmpleado())
                .idRestaurante(request.getIdRestaurante())
                .build();
        RestauranteEmpleado resultado = servicio.asignarEmpleado(restauranteEmpleado);
        RestauranteEmpleadoResponse response = new RestauranteEmpleadoResponse();
        response.setId(resultado.getId());
        response.setIdEmpleado(resultado.getIdEmpleado());
        response.setIdRestaurante(resultado.getIdRestaurante());
        return response;
    }
}
