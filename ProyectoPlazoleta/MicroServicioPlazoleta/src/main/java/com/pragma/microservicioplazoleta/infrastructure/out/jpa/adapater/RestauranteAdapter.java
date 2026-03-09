package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.RestauranteEntityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteAdapter implements IRestauranteRepositorio {

    private final RestauranteRepositorio restauranteRepositorio;
    private final RestauranteEntityMapper restauranteEntityMapper;

    @Override
    public Restaurante guardarRestaurante(Restaurante restaurante) {
        return restauranteEntityMapper.toModel(
                restauranteRepositorio.save(restauranteEntityMapper.toEntity(restaurante))
        );
    }
}
