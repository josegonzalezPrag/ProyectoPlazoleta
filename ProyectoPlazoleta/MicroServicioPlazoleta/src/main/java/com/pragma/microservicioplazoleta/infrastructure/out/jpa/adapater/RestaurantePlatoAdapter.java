package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.RestauranteEntityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestaurantePlatoAdapter implements IRestaurantePlatoRepositorio {
    private final RestauranteRepositorio restauranteRepositorio;
    private final RestauranteEntityMapper restauranteEntityMapper;

    @Override
    public Optional<Restaurante> obtenerRestaurantePorId(Long id) {
        return restauranteRepositorio.findById(id)
                .map(restauranteEntityMapper::toModel);
    }

}
