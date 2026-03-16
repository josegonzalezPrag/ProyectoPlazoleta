package com.pragma.microserviciotrazabilidad.domain.spi;


import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;import java.util.List;

public interface ITrazabilidadRepositorio {
    Trazabilidad guardarTrazabilidad(Trazabilidad trazabilidad);
    List<Trazabilidad> obtenerPorPedido(Long idPedido);
    List<Trazabilidad> obtenerPorRestaurante(Long idRestaurante);
}
