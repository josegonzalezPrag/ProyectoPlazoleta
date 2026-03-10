package com.pragma.microservicioplazoleta.domain.usercase;


import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;

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
            throw new IllegalArgumentException("El propietario no existe");
        }
        if (!restaurante.getTelefono().matches("^\\+?\\d{1,12}$")) {
            throw new IllegalArgumentException("El teléfono debe tener máximo 13 caracteres");
        }
        if (!propietario.getRolNombre().equals("PROPIETARIO")) {
            throw new IllegalArgumentException("El usuario no tiene rol de Propietario");
        }
        return restauranteRepositorio.guardarRestaurante(restaurante);
    }

}