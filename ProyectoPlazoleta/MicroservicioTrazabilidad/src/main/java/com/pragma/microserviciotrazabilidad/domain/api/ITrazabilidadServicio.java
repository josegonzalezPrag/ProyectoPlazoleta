package com.pragma.microserviciotrazabilidad.domain.api;

import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import java.util.List;

public interface ITrazabilidadServicio {
    Trazabilidad registrarCambioEstado(Trazabilidad trazabilidad);
    List<Trazabilidad> obtenerTrazabilidadPedido(Long idPedido);
    long calcularTiempoEntrega(Long idPedido);
}
