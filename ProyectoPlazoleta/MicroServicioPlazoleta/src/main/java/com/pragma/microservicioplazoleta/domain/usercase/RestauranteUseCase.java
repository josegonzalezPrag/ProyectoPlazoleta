package com.pragma.microservicioplazoleta.domain.usercase;


import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.ITrazabilidadClient;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.RestauranteConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.RestauranteNoEncontradoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.UsuarioNoEncontradoException;

import java.util.List;

public class RestauranteUseCase implements IRestauranteServicio {
    private final IRestauranteRepositorio restauranteRepositorio;
    private final IUsuarioClient usuarioClient;
    private final ITrazabilidadClient trazabilidadClient;

    public RestauranteUseCase(IRestauranteRepositorio restauranteRepositorio,
                              IUsuarioClient usuarioClient,
                              ITrazabilidadClient trazabilidadClient) {
        this.restauranteRepositorio = restauranteRepositorio;
        this.usuarioClient = usuarioClient;
        this.trazabilidadClient = trazabilidadClient;
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

    @Override
    public List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante, Long idPropietario) {
        Restaurante restaurante = restauranteRepositorio.obtenerRestaurantePorId(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException(RestauranteConstantes.RESTAURANTE_NO_EXISTE));

        if (!restaurante.getIdPropietario().equals(idPropietario))
            throw new SinPermisosException(RestauranteConstantes.SIN_PERMISO_RESTAURANTE);

        return trazabilidadClient.obtenerEficienciaPorPedido(idRestaurante);
    }

    @Override
    public List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante, Long idPropietario) {
        Restaurante restaurante = restauranteRepositorio.obtenerRestaurantePorId(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException(RestauranteConstantes.RESTAURANTE_NO_EXISTE));

        if (!restaurante.getIdPropietario().equals(idPropietario))
            throw new SinPermisosException(RestauranteConstantes.SIN_PERMISO_RESTAURANTE);

        return trazabilidadClient.obtenerRankingPorEmpleado(idRestaurante);
    }
}