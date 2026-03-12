package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.RestauranteEmpleadoEntiryMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.RestauranteEmpleadoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RestauranteEmpleadoAdapter implements IRestauranteEmpleadoRespositorio {
    private final RestauranteEmpleadoRepositorio repositorio;
    private final RestauranteEmpleadoEntiryMapper mapper;

    @Override
    public RestauranteEmpleado guardarRelacion(RestauranteEmpleado restauranteEmpleado) {
        return mapper.toModel(repositorio.save(mapper.toEntity(restauranteEmpleado)));
    }

    @Override
    public Optional<RestauranteEmpleado> obtenerPorIdEmpleado(Long idEmpleado) {
        return repositorio.findByIdEmpleado(idEmpleado)
                .map(mapper::toModel);
    }
}
