package com.pragma.microservicioplazoleta.domain.spi;



import com.pragma.microservicioplazoleta.domain.model.Restaurante;

import java.util.Optional;

public interface IRestaurantePlatoRepositorio {
    Optional<Restaurante> obtenerRestaurantePorId(Long id);
}
