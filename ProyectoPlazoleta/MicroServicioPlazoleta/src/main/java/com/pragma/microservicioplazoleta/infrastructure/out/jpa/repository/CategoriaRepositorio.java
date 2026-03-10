package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepositorio  extends JpaRepository<CategoriaEntity, Long>{
    Optional<CategoriaEntity> findByNombre(String nombre);
}
