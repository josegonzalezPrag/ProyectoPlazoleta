package com.pragma.microserviciousuario.infrastructure.out.jpa.repository;

import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.UsuarioEntiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository <UsuarioEntiy,Long> {
    Optional<UsuarioEntiy> findByCorreo(String correo);

}
