package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;


import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PlatoEntiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoRepositorio extends JpaRepository<PlatoEntiy, Long> {
    List<PlatoEntiy> findByIdRestaurante(Long idRestaurante);
    List<PlatoEntiy> findByIdRestauranteAndCategoriaId(Long idRestaurante, Long idCategoria);
}

