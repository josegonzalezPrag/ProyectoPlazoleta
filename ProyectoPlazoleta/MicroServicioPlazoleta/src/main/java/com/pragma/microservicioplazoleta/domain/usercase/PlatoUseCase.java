package com.pragma.microservicioplazoleta.domain.usercase;



import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.PlatoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.*;

import java.util.List;


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
        Restaurante restaurante = obtenerRestauranteOFallar(plato.getIdRestaurante());
        Usuario propietario = usuarioClient.obtenerUsuarioPorId(restaurante.getIdPropietario());

        if (propietario == null) {
            throw new UsuarioNoEncontradoException(PlatoConstantes.PROPIETARIO_NO_EXISTE);
        }
        if (!propietario.getRolNombre().equals(PlatoConstantes.ROL_PROPIETARIO)) {
            throw new SinPermisosException(PlatoConstantes.ROL_NO_PROPIETARIO);
        }
        if (plato.getPrecio() <= 0) {
            throw new DatoInvalidoException(PlatoConstantes.PRECIO_INVALIDO);
        }
        if (plato.getCategoria() == null) {
            throw new DatoInvalidoException(PlatoConstantes.CATEGORIA_NULA);
        }
        if (plato.getCategoria().getId() == null) {
            throw new DatoInvalidoException(PlatoConstantes.CATEGORIA_SIN_ID);
        }
        if (!platoRepositorio.categoriaExiste(plato.getCategoria().getId())) {
            throw new DatoInvalidoException(PlatoConstantes.CATEGORIA_NO_ENCONTRADA);
        }
        plato.setActivo(true);
        return platoRepositorio.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Long id, Long precio, String descripcion) {
        Plato plato = platoRepositorio.obtenerPlatoPorId(id)
                .orElseThrow(() -> new PlatoNoEncontradoException(PlatoConstantes.PLATO_NO_EXISTE));

        if (precio <= 0) throw new DatoInvalidoException(PlatoConstantes.PRECIO_INVALIDO);

        plato.setPrecio(precio);
        plato.setDescripcion(descripcion);
        return platoRepositorio.guardarPlato(plato);
    }

    @Override
    public Plato cambiarEstadoPlato(Long idPlato, Boolean activo, Long idPropietarioAutenticado) {
        Plato plato = platoRepositorio.obtenerPlatoPorId(idPlato)
                .orElseThrow(() -> new PlatoNoEncontradoException(PlatoConstantes.PLATO_NO_EXISTE));

        Restaurante restaurante = obtenerRestauranteOFallar(plato.getIdRestaurante());

        if (!restaurante.getIdPropietario().equals(idPropietarioAutenticado))
            throw new SinPermisosException(PlatoConstantes.SIN_PERMISO_PLATO);

        plato.setActivo(activo);
        return platoRepositorio.guardarPlato(plato);
    }

    @Override
    public List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano) {
        Restaurante restaurante = obtenerRestauranteOFallar(idRestaurante);
        return platoRepositorio.listarPlatosPorRestaurante(restaurante.getId(), idCategoria, pagina, tamano)
                .stream()
                .filter(Plato::getActivo)
                .toList();
    }

    private Restaurante obtenerRestauranteOFallar(Long idRestaurante) {
        return restauranteRepositorio.obtenerRestaurantePorId(idRestaurante)
                .orElseThrow(() -> new RestauranteNoEncontradoException(PlatoConstantes.RESTAURANTE_NO_EXISTE));
    }


}
