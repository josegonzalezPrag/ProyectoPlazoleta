package com.pragma.microservicioplazoleta.domain.api;


import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import java.util.List;

public interface IRestauranteServicio {
    Restaurante crearRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes(int pagina, int tamano);
}
