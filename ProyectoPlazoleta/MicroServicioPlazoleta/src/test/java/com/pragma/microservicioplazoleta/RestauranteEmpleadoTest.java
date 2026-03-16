package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestaurantePlatoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteEmpleadoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.RestauranteEmpleadoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.RestauranteNoEncontradoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.UsuarioNoEncontradoException;
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
class RestauranteEmpleadoTest {
    @Mock
    private IRestauranteEmpleadoRespositorio repositorio;

    @Mock
    private IRestaurantePlatoRepositorio restauranteRepositorio;

    @Mock
    private IUsuarioClient usuarioClient;

    @InjectMocks
    private RestauranteEmpleadoUseCase restauranteEmpleadoUseCase;

    private RestauranteEmpleado relacionValida;
    private Restaurante restauranteValido;
    private Usuario empleadoValido;

    @BeforeEach
    void setUp() {
        relacionValida = RestauranteEmpleado.builder()
                .idEmpleado(1L)
                .idRestaurante(1L)
                .build();

        restauranteValido = Restaurante.builder()
                .id(1L)
                .nombre("El Ranchito")
                .idPropietario(2L)
                .build();

        empleadoValido = new Usuario();
        empleadoValido.setId(1L);
        empleadoValido.setRolNombre(RestauranteEmpleadoConstantes.ROL_EMPLEADO);
    }

    @Test
    void deberiaAsignarEmpleadoExitosamente() {
        when(restauranteRepositorio.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(empleadoValido);
        when(repositorio.guardarRelacion(any())).thenReturn(relacionValida);

        RestauranteEmpleado resultado = restauranteEmpleadoUseCase.asignarEmpleado(relacionValida);

        assertNotNull(resultado);
        verify(repositorio, times(1)).guardarRelacion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoRestauranteNoExiste() {
        when(restauranteRepositorio.obtenerRestaurantePorId(1L)).thenReturn(Optional.empty());

        assertThrows(RestauranteNoEncontradoException.class,
                () -> restauranteEmpleadoUseCase.asignarEmpleado(relacionValida));

        verify(repositorio, never()).guardarRelacion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmpleadoNoExiste() {
        when(restauranteRepositorio.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(null);

        assertThrows(UsuarioNoEncontradoException.class,
                () -> restauranteEmpleadoUseCase.asignarEmpleado(relacionValida));

        verify(repositorio, never()).guardarRelacion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoTieneRolEmpleado() {
        Usuario noEsEmpleado = new Usuario();
        noEsEmpleado.setRolNombre("CLIENTE");

        when(restauranteRepositorio.obtenerRestaurantePorId(1L)).thenReturn(Optional.of(restauranteValido));
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(noEsEmpleado);

        assertThrows(SinPermisosException.class,
                () -> restauranteEmpleadoUseCase.asignarEmpleado(relacionValida));

        verify(repositorio, never()).guardarRelacion(any());
    }
}
