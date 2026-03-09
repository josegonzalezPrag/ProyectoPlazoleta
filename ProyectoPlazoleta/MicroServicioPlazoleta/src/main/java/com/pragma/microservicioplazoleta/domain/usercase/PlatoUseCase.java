package com.pragma.microservicioplazoleta.domain.usercase;



import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;

public class PlatoUseCase implements  IPlatoServicio{
    private final IPlatoRepositorio platoRepositorio;
    private final IRestaurantePlatoRepositorio restauranteRepositorio;
    private final IUsuarioClient usuarioClient;

    public PlatoUseCase(IPlatoRepositorio platoRepositorio,
                        IRestaurantePlatoRepositorio restauranteRepositorio,
                        IUsuarioClient usuarioClient) {
        this.platoRepositorio = platoRepositorio;
        this.restauranteRepositorio = restauranteRepositorio;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public Plato crearPlato(Plato plato) {
        Restaurante restaurante = restauranteRepositorio
                .obtenerRestaurantePorId(plato.getIdRestaurante())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));

        var propietario = usuarioClient.obtenerUsuarioPorId(restaurante.getIdPropietario());

        if (propietario == null) {
            throw new IllegalArgumentException("El propietario no existe");
        }

        if (plato.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        plato.setActivo(true);
        return platoRepositorio.guardarPlato(plato);
    }



}
