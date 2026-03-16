package com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.repository;

import com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.entity.TrazabilidadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrazabilidadRepositorio extends MongoRepository<TrazabilidadEntity, String> {
    List<TrazabilidadEntity> findByIdPedidoOrderByFechaCambioAsc(Long idPedido);
    List<TrazabilidadEntity> findByIdRestaurante(Long idRestaurante);
}
