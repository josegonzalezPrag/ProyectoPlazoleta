package com.pragma.microservicioplazoleta.domain.api;


import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import java.util.List;

public interface IRestauranteServicio {
    Restaurante crearRestaurante(Restaurante restaurante);
    List<Restaurante> listarRestaurantes(int pagina, int tamano);
    List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante, Long idPropietario);
    List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante, Long idPropietario);
}
