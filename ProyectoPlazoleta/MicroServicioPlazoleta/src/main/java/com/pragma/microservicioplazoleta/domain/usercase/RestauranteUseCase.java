package com.pragma.microservicioplazoleta.domain.usercase;


import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.RestauranteConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.UsuarioNoEncontradoException;

import java.util.List;

public class RestauranteUseCase implements IRestauranteServicio {
    private final IRestauranteRepositorio restauranteRepositorio;
    private final IUsuarioClient usuarioClient;

    public RestauranteUseCase(IRestauranteRepositorio restauranteRepositorio,
                              IUsuarioClient usuarioClient) {
        this.restauranteRepositorio = restauranteRepositorio;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public Restaurante crearRestaurante(Restaurante restaurante) {
        Usuario propietario = usuarioClient.obtenerUsuarioPorId(restaurante.getIdPropietario());

        if (propietario == null) {
            throw new UsuarioNoEncontradoException(RestauranteConstantes.PROPIETARIO_NO_EXISTE);
        }
        if (!restaurante.getTelefono().matches(RestauranteConstantes.REGEX_TELEFONO)) {
            throw new DatoInvalidoException(RestauranteConstantes.TELEFONO_INVALIDO);
        }
        if (!propietario.getRolNombre().equals(RestauranteConstantes.ROL_PROPIETARIO)) {
            throw new SinPermisosException(RestauranteConstantes.ROL_NO_PROPIETARIO);
        }
        return restauranteRepositorio.guardarRestaurante(restaurante);
    }

    @Override
    public List<Restaurante> listarRestaurantes(int pagina, int tamano) {
        return restauranteRepositorio.listarRestaurantes(pagina, tamano);
    }
}