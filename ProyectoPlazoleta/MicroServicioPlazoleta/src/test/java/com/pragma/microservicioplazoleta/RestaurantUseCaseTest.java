package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.RestauranteConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
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

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

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
        propietarioValido.setRolNombre(RestauranteConstantes.ROL_PROPIETARIO);
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

        assertThrows(UsuarioNoEncontradoException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido));

        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoUsuarioNoEsPropietario() {
        propietarioValido.setRolNombre("ADMINISTRADOR");
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        assertThrows(SinPermisosException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido));

        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoTelefonoEsInvalido() {
        restauranteValido.setTelefono("+5730156789012345");
        when(iUsuarioClient.obtenerUsuarioPorId(2L)).thenReturn(propietarioValido);

        assertThrows(DatoInvalidoException.class,
                () -> restauranteUseCase.crearRestaurante(restauranteValido));

        verify(iRestauranteRepositorio, never()).guardarRestaurante(any());
    }
}
