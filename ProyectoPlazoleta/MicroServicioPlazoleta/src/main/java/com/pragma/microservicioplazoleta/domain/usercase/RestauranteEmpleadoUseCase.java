package com.pragma.microservicioplazoleta.domain.usercase;

import com.pragma.microservicioplazoleta.domain.api.IRestauranteEmpleadoServicio;
import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.RestauranteEmpleadoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.RestauranteNoEncontradoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.UsuarioNoEncontradoException;

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
                .orElseThrow(() -> new RestauranteNoEncontradoException(RestauranteEmpleadoConstantes.RESTAURANTE_NO_EXISTE));

        Usuario empleado = usuarioClient.obtenerUsuarioPorId(restauranteEmpleado.getIdEmpleado());

        if (empleado == null) throw new UsuarioNoEncontradoException(RestauranteEmpleadoConstantes.EMPLEADO_NO_EXISTE);
        if (!empleado.getRolNombre().equals(RestauranteEmpleadoConstantes.ROL_EMPLEADO)) throw new SinPermisosException(RestauranteEmpleadoConstantes.ROL_NO_EMPLEADO);

        return repositorio.guardarRelacion(restauranteEmpleado);
    }
}