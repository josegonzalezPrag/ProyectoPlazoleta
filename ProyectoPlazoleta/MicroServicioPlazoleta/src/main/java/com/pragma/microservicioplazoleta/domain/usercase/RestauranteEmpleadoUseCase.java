package com.pragma.microservicioplazoleta.domain.usercase;

import com.pragma.microservicioplazoleta.domain.api.IRestauranteEmpleadoServicio;
import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;

public class RestauranteEmpleadoUseCase implements IRestauranteEmpleadoServicio {
    private final IRestauranteEmpleadoRespositorio repositorio;
    private final IRestaurantePlatoRepositorio restauranteRepositorio;
    private final IUsuarioClient usuarioClient;

    public RestauranteEmpleadoUseCase(IRestauranteEmpleadoRespositorio repositorio,
                                      IRestaurantePlatoRepositorio restauranteRepositorio,
                                      IUsuarioClient usuarioClient) {
        this.repositorio = repositorio;
        this.restauranteRepositorio = restauranteRepositorio;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public RestauranteEmpleado asignarEmpleado(RestauranteEmpleado restauranteEmpleado) {
        restauranteRepositorio.obtenerRestaurantePorId(restauranteEmpleado.getIdRestaurante())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));

        Usuario empleado = usuarioClient.obtenerUsuarioPorId(restauranteEmpleado.getIdEmpleado());

        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no existe");
        }
        if (!empleado.getRolNombre().equals("EMPLEADO")) {
            throw new IllegalArgumentException("El usuario no tiene rol de Empleado");
        }

        return repositorio.guardarRelacion(restauranteEmpleado);
    }
}
