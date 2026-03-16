package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestaurabteEntity;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.RestauranteEntityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


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

    @Override
    public List<Restaurante> listarRestaurantes(int pagina, int tamano) {
        Pageable pageable = PageRequest.of(pagina, tamano, Sort.by("nombre").ascending());
        List<RestaurabteEntity> entities = restauranteRepositorio.findAll(pageable).getContent();
        return entities.stream()
                .map(restauranteEntityMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Restaurante> obtenerRestaurantePorId(Long idRestaurante) {
        return restauranteRepositorio.findById(idRestaurante)
                .map(restauranteEntityMapper::toModel);
    }
}
