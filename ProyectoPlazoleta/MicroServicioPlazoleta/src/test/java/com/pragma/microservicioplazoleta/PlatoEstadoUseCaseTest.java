package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Categoria;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
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
class PlatoEstadoUseCaseTest {
    @Mock
    private IPlatoRepositorio iPlatoRepositorio;

    @Mock
    private IRestaurantePlatoRepositorio iRestauranteRepositorioPlato;


    @InjectMocks
    private PlatoUseCase platoUseCase;

    private Plato platoValido;
    private Restaurante restauranteValido;

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
    void deberiaHabilitarPlatoExitosamente() {
        platoValido.setActivo(false);

        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoValido);

        Plato resultado = platoUseCase.cambiarEstadoPlato(1L, true, 2L);

        assertNotNull(resultado);
        assertTrue(platoValido.getActivo());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatoNoExisteEnCambioEstado() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.cambiarEstadoPlato(1L, false, 2L)
        );

        assertEquals("El plato no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoRestauranteNoExisteEnCambioEstado() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.cambiarEstadoPlato(1L, false, 2L)
        );

        assertEquals("El restaurante no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPropietarioNoEsDuenioDelRestaurante() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoValido));
        when(iRestauranteRepositorioPlato.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.cambiarEstadoPlato(1L, false, 99L)
        );

        assertEquals("No tienes permiso para modificar este plato", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }
}
