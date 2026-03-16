package com.pragma.microservicioplazoleta.aplication.handler;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoPedidoResponse;

import java.util.List;

public interface IRestauranteHandler {
    RestauranteResponse crearRestaurante(RestauranteRequest request);
    List<RestauranteResponse> listarRestaurantes(int pagina, int tamano);
    List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante, Long idPropietario);
    List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante, Long idPropietario);
}
