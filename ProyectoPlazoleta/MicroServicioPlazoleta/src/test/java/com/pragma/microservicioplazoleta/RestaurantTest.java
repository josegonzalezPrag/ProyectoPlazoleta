package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Rol;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    @Mock
    private IRestauranteRepositorio iRestauranteRepositorio;

    @Mock
    private IUsuarioClient iUsuarioClient;

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;

    private Restaurante restauranteValido;
    private Usuario propietarioValido;

    @BeforeEach
    void setUp() {
        Rol rolPropietario = new Rol();
        rolPropietario.setNombre("PROPIETARIO");

        restauranteValido = Restaurante.builder()
                .nombre("El Ranchito")
                .nit(900123456)
                .direccion("Calle 10 # 5-20")
                .telefono("+573015678901")
                .urlLogo("https://logos.com/ranchito.png")
                .idPropietario(2L)
                .build();

        propietarioValido = new Usuario();
        propietarioValido.setId(2L);
        propietarioValido.setRolNombre("PROPIETARIO");
    }

    @Test
    void deberiaCrearRestauranteExitosamente() {
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        when(iRestauranteRepositorio.guardarRestaurante(restauranteValido)).thenReturn(restauranteValido);

        Restaurante resultado = restauranteUseCase.crearRestaurante(restauranteValido);

        assertNotNull(resultado);
        verify(iRestauranteRepositorio, times(1)).guardarRestaurante(restauranteValido);
    }

    @Test
    void deberiaLanzarExcepcionCuandoPropietarioNoExiste() {
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido)
        );

        assertEquals("El propietario no existe", exception.getMessage());
        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoEsPropietario() {
        propietarioValido.setRolNombre("ADMINISTRADOR");
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido)
        );

        assertEquals("El usuario no tiene rol de Propietario", exception.getMessage());
        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTelefonoExcede13Caracteres() {
        propietarioValido.setRolNombre("PROPIETARIO");
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);
        restauranteValido.setTelefono("+5730156789012345");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido)
        );

        assertEquals("El teléfono debe tener máximo 13 caracteres", exception.getMessage());
        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }
}
