package com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.adapater;

import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import com.pragma.microserviciotrazabilidad.domain.spi.ITrazabilidadRepositorio;
import com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.mapper.TrazabilidadEntityMapper;
import com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.repository.TrazabilidadRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrazabilidadAdapter implements ITrazabilidadRepositorio {
    private final TrazabilidadRepositorio repository;
    private final TrazabilidadEntityMapper mapper;

    @Override
    public Trazabilidad guardarTrazabilidad(Trazabilidad trazabilidad) {
        return mapper.toModel(repository.save(mapper.toEntity(trazabilidad)));
    }

    @Override
    public List<Trazabilidad> obtenerPorPedido(Long idPedido) {
        return repository.findByIdPedidoOrderByFechaCambioAsc(idPedido)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public List<Trazabilidad> obtenerPorRestaurante(Long idRestaurante) {
        return repository.findByIdRestaurante(idRestaurante)
                .stream()
                .map(mapper::toModel)
                .toList();
    }
}
