package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Categoria;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.PlatoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.PlatoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlatoUseCaseTest {
    @Mock
    private IPlatoRepositorio iPlatoRepositorio;

    @Mock
    private IRestaurantePlatoRepositorio iRestauranteRepositorioPlato;

    @Mock
    private IUsuarioClient iUsuarioClient;

    @InjectMocks
    private PlatoUseCase platoUseCase;

    private Plato platoValido;
    private Restaurante restauranteValido;
    private Usuario propietarioValido;

    @BeforeEach
    void setUp() {
        Categoria categoriaValida = new Categoria();
        categoriaValida.setId(1L);
        categoriaValida.setNombre("Típica colombiana");
        categoriaValida.setDescripcion("Platos tradicionales de Colombia");

        platoValido = Plato.builder()
                .id(1L)
                .nombre("Bandeja Paisa")
                .precio(25000L)
                .descripcion("Plato típico colombiano")
                .urlImagen("https://images.com/bandeja.png")
                .categoria(categoriaValida)
                .activo(true)
                .idRestaurante(1L)
                .build();

        restauranteValido = Restaurante.builder()
                .id(1L)
                .nombre("El Ranchito")
                .idPropietario(2L)
                .build();

        propietarioValido = new Usuario();
        propietarioValido.setId(2L);
        propietarioValido.setCorreo("propietario@restaurante.com");
        propietarioValido.setRolNombre(PlatoConstantes.ROL_PROPIETARIO);
    }

    @Test
    void deberiaCrearPlatoExitosamente() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        when(iPlatoRepositorio.categoriaExiste(1L)).thenReturn(true);
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        Plato resultado = platoUseCase.crearPlato(platoValido);

        assertNotNull(resultado);
        assertTrue(platoValido.getActivo());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoRestauranteNoExiste() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.empty());

        assertThrows(RestauranteNoEncontradoException.class,
                () -> platoUseCase.crearPlato(platoValido));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPropietarioNoExiste() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(null);

        assertThrows(UsuarioNoEncontradoException.class,
                () -> platoUseCase.crearPlato(platoValido));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoEsPropietario() {
        Usuario noEsPropietario = new Usuario();
        noEsPropietario.setRolNombre("CLIENTE");

        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(noEsPropietario);

        assertThrows(SinPermisosException.class,
                () -> platoUseCase.crearPlato(platoValido));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPrecioEsCero() {
        platoValido.setPrecio(0L);
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        assertThrows(DatoInvalidoException.class,
                () -> platoUseCase.crearPlato(platoValido));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCategoriaNoExiste() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        when(iPlatoRepositorio.categoriaExiste(1L)).thenReturn(false);

        assertThrows(DatoInvalidoException.class,
                () -> platoUseCase.crearPlato(platoValido));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaActualizarPlatoExitosamente() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        Plato resultado = platoUseCase.actualizarPlato(1L, 28000L, "Nueva descripcion");

        assertNotNull(resultado);
        assertEquals(28000L, platoValido.getPrecio());
        assertEquals("Nueva descripcion", platoValido.getDescripcion());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatoNoExisteAlActualizar() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.empty());

        assertThrows(PlatoNoEncontradoException.class,
                () -> platoUseCase.actualizarPlato(1L, 28000L, "Nueva descripcion"));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPrecioEsNegativo() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));

        assertThrows(DatoInvalidoException.class,
                () -> platoUseCase.actualizarPlato(1L, -5000L, "Nueva descripcion"));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaCambiarEstadoPlatoExitosamente() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        Plato resultado = platoUseCase.cambiarEstadoPlato(1L, false, 2L);

        assertNotNull(resultado);
        assertFalse(platoValido.getActivo());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatoNoExisteEnCambioEstado() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.empty());

        assertThrows(PlatoNoEncontradoException.class,
                () -> platoUseCase.cambiarEstadoPlato(1L, false, 2L));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPropietarioNoEsDuenioDelRestaurante() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));

        assertThrows(SinPermisosException.class,
                () -> platoUseCase.cambiarEstadoPlato(1L, false, 99L));

        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }
}