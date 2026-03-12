package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PlatoEntiy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoPedidoRepositorio extends JpaRepository<PlatoEntiy, Long> {
    boolean existsByIdAndIdRestaurante(Long id, Long idRestaurante);
}
