package com.pragma.microserviciousuario.infrastructure.out.jpa.repository;

import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepositorio extends JpaRepository<RolEntity, Long>{
    Optional<RolEntity> findByNombre(String nombre);
}
