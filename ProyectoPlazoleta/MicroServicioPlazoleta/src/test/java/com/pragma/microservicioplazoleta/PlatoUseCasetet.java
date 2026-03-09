package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Rol;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoUseCasetet {
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
        platoValido = Plato.builder()
                .nombre("Bandeja Paisa")
                .precio(25000L)
                .descripcion("Plato típico colombiano")
                .urlImagen("https://images.com/bandeja.png")
                .categoria("Típica colombiana")
                .idRestaurante(1L)
                .build();

        restauranteValido = Restaurante.builder()
                .id(1L)
                .nombre("El Ranchito")
                .idPropietario(2L)
                .build();

        propietarioValido = new Usuario();
        propietarioValido.setId(2L);
        propietarioValido.setRol(Rol.PROPIETARIO);
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
}
