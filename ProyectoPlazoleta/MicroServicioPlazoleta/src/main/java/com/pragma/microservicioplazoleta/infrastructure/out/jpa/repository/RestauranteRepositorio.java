package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;


import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestaurabteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestauranteRepositorio extends JpaRepository<RestaurabteEntity, Long> {
    Page<RestaurabteEntity> findAll(Pageable pageable);
}
