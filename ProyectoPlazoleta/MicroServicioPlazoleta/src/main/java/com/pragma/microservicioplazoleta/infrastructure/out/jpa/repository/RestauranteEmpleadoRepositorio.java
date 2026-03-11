package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteEmpleadoRepositorio extends JpaRepository<RestauranteEmpleadoEntity, Long> {
}
