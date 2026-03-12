package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;

import java.util.Optional;

public interface IRestauranteEmpleadoRespositorio {
    RestauranteEmpleado guardarRelacion(RestauranteEmpleado restauranteEmpleado);
    Optional<RestauranteEmpleado> obtenerPorIdEmpleado(Long idEmpleado);
}
