package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Plato;
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
public class PlatoEditTest {

    @Mock
    private IPlatoRepositorio iPlatoRepositorio;

    @Mock
    private IRestaurantePlatoRepositorio iRestauranteRepositorioPlato;

    @Mock
    private IUsuarioClient iUsuarioClient;

    @InjectMocks
    private PlatoUseCase platoUseCase;

    private Plato platoExistente;

    @BeforeEach
    void setUp() {
        platoExistente = Plato.builder()
                .id(1L)
                .nombre("Bandeja Paisa")
                .precio(25000L)
                .descripcion("Plato típico colombiano")
                .urlImagen("https://images.com/bandeja.png")
                .categoria("Típica colombiana")
                .activo(true)
                .idRestaurante(1L)
                .build();
    }

    @Test
    void deberiaActualizarPlatoExitosamente() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoExistente));
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoExistente);

        Plato resultado = platoUseCase.actualizarPlato(1L, 28000L, "Nueva descripcion");

        assertNotNull(resultado);
        assertEquals(28000L, platoExistente.getPrecio());
        assertEquals("Nueva descripcion", platoExistente.getDescripcion());
        verify(iPlatoRepositorio, times(1)).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatoNoExiste() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.actualizarPlato(1L, 28000L, "Nueva descripcion")
        );

        assertEquals("El plato no existe", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaActualizarSoloPrecioYDescripcion() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoExistente));
        when(iPlatoRepositorio.guardarPlato(any())).thenReturn(platoExistente);

        platoUseCase.actualizarPlato(1L, 30000L, "Descripcion actualizada");

        assertEquals(30000L, platoExistente.getPrecio());
        assertEquals("Descripcion actualizada", platoExistente.getDescripcion());
        assertEquals("Bandeja Paisa", platoExistente.getNombre());
        assertEquals("Típica colombiana", platoExistente.getCategoria());
        assertEquals("https://images.com/bandeja.png", platoExistente.getUrlImagen());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPrecioEsCero() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.actualizarPlato(1L, 0L, "Nueva descripcion")
        );

        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPrecioEsNegativo() {
        when(iPlatoRepositorio.obtenerPlatoPorId(1L)).thenReturn(Optional.of(platoExistente));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> platoUseCase.actualizarPlato(1L, -5000L, "Nueva descripcion")
        );

        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
        verify(iPlatoRepositorio, never()).guardarPlato(any());
    }
}