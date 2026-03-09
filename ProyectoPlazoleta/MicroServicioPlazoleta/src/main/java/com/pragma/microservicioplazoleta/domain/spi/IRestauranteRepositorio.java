package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;

public interface IRestauranteRepositorio {
    Restaurante guardarRestaurante(Restaurante restaurante);
}
