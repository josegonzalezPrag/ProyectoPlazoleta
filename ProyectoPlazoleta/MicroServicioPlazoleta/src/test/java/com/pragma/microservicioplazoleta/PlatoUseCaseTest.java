package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Categoria;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.PlatoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
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
                .nombre("Bandeja Paisa")
                .precio(25000L)
                .descripcion("Plato típico colombiano")
                .urlImagen("https://images.com/bandeja.png")
                .categoria(categoriaValida)
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
        propietarioValido.setRolNombre("PROPIETARIO");
    }

    @Test
    void deberiaCrearPlatoExitosamente() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        Plato resultado = platoUseCase.crearPlato(platoValido);

        assertNotNull(resultado);
        assertTrue(platoValido.getActivo());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoRestauranteNoExiste() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("El restaurante no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPropietarioNoExiste() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("El propietario no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoEsPropietario() {
        Usuario noEsPropietario = new Usuario();
        noEsPropietario.setId(3L);
        noEsPropietario.setCorreo("cliente@restaurante.com");
        noEsPropietario.setRolNombre("CLIENTE");

        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(noEsPropietario);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("El usuario no tiene rol de Propietario", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaSetearActivoEnTrueAlCrear() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        platoUseCase.crearPlato(platoValido);

        assertTrue(platoValido.getActivo());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPrecioEsCero() {
        platoValido.setPrecio(0L);
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCategoriaEsNula() {
        platoValido.setCategoria(null);
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("La categoría no puede ser nula", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCategoriaNoTieneId() {
        Categoria sinId = new Categoria();
        sinId.setNombre("Sin ID");
        platoValido.setCategoria(sinId);

        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.crearPlato(platoValido)
        );

        assertEquals("La categoría debe tener un ID válido", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaListarPlatosPorRestauranteExitosamente() {
        List<Plato> platos = List.of(
                platoValido,
                Plato.builder()
                        .nombre("Ajiaco")
                        .precio(20000L)
                        .descripcion("Sopa típica bogotana")
                        .urlImagen("https://images.com/ajiaco.png")
                        .categoria(platoValido.getCategoria())
                        .idRestaurante(1L)
                        .build()
        );

        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iPlatoRepositorio.listarPlatosPorRestaurante(1L, null, 0, 10)).thenReturn(platos);

        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(1L, null, 0, 10);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(iPlatoRepositorio, times(1)).listarPlatosPorRestaurante(1L, null, 0, 10);
    }

    @Test
    void deberiaListarPlatosFiltradosPorCategoria() {
        List<Plato> platos = List.of(platoValido);

        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iPlatoRepositorio.listarPlatosPorRestaurante(1L, 1L, 0, 10)).thenReturn(platos);

        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(1L, 1L, 0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Típica colombiana", resultado.getFirst().getCategoria().getNombre());
        verify(iPlatoRepositorio, times(1)).listarPlatosPorRestaurante(1L, 1L, 0, 10);
    }

    @Test
    void deberiaLanzarExcepcionCuandoRestauranteNoExisteAlListar() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.listarPlatosPorRestaurante(1L, null, 0, 10)
        );

        assertEquals("El restaurante no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).listarPlatosPorRestaurante(any(), any(), anyInt(), anyInt());
    }

    @Test
    void deberiaRetornarListaVaciaCuandoNoHayPlatos() {
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iPlatoRepositorio.listarPlatosPorRestaurante(1L, null, 0, 10)).thenReturn(List.of());

        List<Plato> resultado = platoUseCase.listarPlatosPorRestaurante(1L, null, 0, 10);

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}