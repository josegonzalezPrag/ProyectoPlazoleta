package com.pragma.microservicioplazoleta.domain.api;


import com.pragma.microservicioplazoleta.domain.model.Plato;

public interface IPlatoServicio {
    Plato crearPlato(Plato plato);
    Plato actualizarPlato(Long id,Long precio,String descricion);
}
