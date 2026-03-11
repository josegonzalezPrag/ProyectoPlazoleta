package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.Plato;

import java.util.List;
import java.util.Optional;

public interface IPlatoRepositorio {
    Plato guardarPlato(Plato plato);
    Optional<Plato> obtenerPlatoPorId(Long id);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano);
}
