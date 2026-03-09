package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.PlatoEnrityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.PlatoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlatoAdapter implements IPlatoRepositorio {
    private final PlatoRepositorio platoRepositorio;
    private final PlatoEnrityMapper platoEntityMapper;

    @Override
    public Plato guardarPlato(Plato plato) {
        return platoEntityMapper.toModel(
                platoRepositorio.save(platoEntityMapper.toEntidy(plato))
        );
    }

    @Override
    public Optional<Plato> obtenerPlatoPorId(Long id) {
        return platoRepositorio.findById(id)
                .map(platoEntityMapper::toModel);
    }

}
