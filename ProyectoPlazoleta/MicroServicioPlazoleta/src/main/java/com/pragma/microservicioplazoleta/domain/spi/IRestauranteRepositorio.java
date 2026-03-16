package com.pragma.microservicioplazoleta.domain.spi;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;

import java.util.List;
import java.util.Optional;


public interface IRestauranteRepositorio {
    Restaurante guardarRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes(int pagina, int tamano);
    Optional<Restaurante> obtenerRestaurantePorId(Long idRestaurante);

}
