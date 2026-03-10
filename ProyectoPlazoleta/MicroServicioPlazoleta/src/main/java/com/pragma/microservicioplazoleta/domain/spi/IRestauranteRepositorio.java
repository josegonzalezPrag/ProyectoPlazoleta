package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;

import java.util.List;


public interface IRestauranteRepositorio {
    Restaurante guardarRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes(int pagina, int tamano);
}
