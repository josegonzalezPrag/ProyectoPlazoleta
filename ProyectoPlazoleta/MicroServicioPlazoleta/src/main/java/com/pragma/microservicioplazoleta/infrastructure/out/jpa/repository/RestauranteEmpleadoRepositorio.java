package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteEmpleadoRepositorio extends JpaRepository<RestauranteEmpleadoEntity, Long> {
    Optional<RestauranteEmpleadoEntity> findByIdEmpleado(Long idEmpleado);
}
