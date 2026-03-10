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

        if (plato.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (!propietario.getRolNombre().equals("PROPIETARIO")) {
            throw new IllegalArgumentException("El usuario no tiene rol de Propietario");
        }
        plato.setActivo(true);
        return platoRepositorio.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Long id, Long precio, String descripcion) {
        Plato plato = platoRepositorio.obtenerPlatoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("El plato no existe"));

        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        plato.setPrecio(precio);
        plato.setDescripcion(descripcion);

        return platoRepositorio.guardarPlato(plato);
    }

    @Override
    public Plato cambiarEstadoPlato(Long idPlato, Boolean activo, Long idPropietarioAutenticado) {
        Plato plato = platoRepositorio.obtenerPlatoPorId(idPlato)
                .orElseThrow(() -> new IllegalArgumentException("El plato no existe"));

        Restaurante restaurante = restauranteRepositorio.obtenerRestaurantePorId(plato.getIdRestaurante())
                .orElseThrow(() -> new IllegalArgumentException("El restaurante no existe"));

        if (!restaurante.getIdPropietario().equals(idPropietarioAutenticado)) {
            throw new IllegalArgumentException("No tienes permiso para modificar este plato");
        }

        plato.setActivo(activo);
        return platoRepositorio.guardarPlato(plato);
    }



}
