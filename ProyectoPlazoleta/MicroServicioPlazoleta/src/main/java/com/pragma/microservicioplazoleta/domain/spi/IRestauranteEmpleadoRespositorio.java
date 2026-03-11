package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;

public interface IRestauranteEmpleadoRespositorio {
    RestauranteEmpleado guardarRelacion(RestauranteEmpleado restauranteEmpleado);
}
