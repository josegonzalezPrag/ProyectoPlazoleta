package com.pragma.microservicioplazoleta.domain.api;


import com.pragma.microservicioplazoleta.domain.model.Plato;

import java.util.List;

public interface IPlatoServicio {
    Plato crearPlato(Plato plato);
    Plato actualizarPlato(Long id,Long precio,String descricion);
    Plato cambiarEstadoPlato(Long idPlato, Boolean activo, Long idPropietarioAutenticado);
    List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano);
}
