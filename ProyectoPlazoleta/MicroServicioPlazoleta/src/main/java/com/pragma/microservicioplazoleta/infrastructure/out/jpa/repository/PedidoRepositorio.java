package com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository;

import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepositorio extends JpaRepository<PedidoEntity, Long> {
    boolean existsByIdClienteAndEstadoIn(Long idCliente, List<String> estados);
    boolean existsByIdAndIdRestaurante(Long id, Long idRestaurante);
    List<PedidoEntity> findByIdRestauranteAndEstado(Long idRestaurante, String estado);
    List<PedidoEntity> findByIdCliente(Long idCliente);

}
