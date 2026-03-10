package com.pragma.microserviciousuario;
import com.pragma.microserviciousuario.aplication.dto.request.LoginRequest;
import com.pragma.microserviciousuario.aplication.dto.response.LoginResponse;
import com.pragma.microserviciousuario.domain.model.Rol;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.infrastructure.configuration.AuthService;
import com.pragma.microserviciousuario.infrastructure.configuration.JwTConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private IUsuarioRepositorio iUsuarioRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwTConfig jwtConfig;

    @InjectMocks
    private AuthService authService;

    private Usuario adminValido;
    private Usuario propietarioValido;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        Rol rolAdmin = new Rol();
        rolAdmin.setNombre("ADMINISTRADOR");

        Rol rolPropietario = new Rol();
        rolPropietario.setNombre("PROPIETARIO");

        adminValido = Usuario.builder()
                .id(1L)
                .correo("admin@plazoleta.com")
                .clave("$2a$10$hashedPassword")
                .rol(rolAdmin)
                .build();

        propietarioValido = Usuario.builder()
                .id(2L)
                .correo("carlos@gmail.com")
                .clave("$2a$10$hashedPassword")
                .rol(rolPropietario)
                .build();

        loginRequest = new LoginRequest();
        loginRequest.setCorreo("admin@plazoleta.com");
        loginRequest.setClave("clave1234");
    }

    @Test
    void deberiaLoginExitosamenteComoAdmin() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("admin@plazoleta.com"))
                .thenReturn(Optional.of(adminValido));
        when(passwordEncoder.matches("clave1234", adminValido.getClave())).thenReturn(true);
        when(jwtConfig.generarToken("admin@plazoleta.com", "ADMINISTRADOR")).thenReturn("tokenAdmin");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("tokenAdmin", response.getToken());
    }

    @Test
    void deberiaLoginExitosamenteComoPropietario() {
        loginRequest.setCorreo("carlos@gmail.com");
        loginRequest.setClave("clave1234");

        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com"))
                .thenReturn(Optional.of(propietarioValido));
        when(passwordEncoder.matches("clave1234", propietarioValido.getClave())).thenReturn(true);
        when(jwtConfig.generarToken("carlos@gmail.com", "PROPIETARIO")).thenReturn("tokenPropietario");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("tokenPropietario", response.getToken());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoNoExiste() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("admin@plazoleta.com"))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals("Credenciales incorrectas", exception.getMessage());
    }

    @Test
    void deberiaLanzarExcepcionCuandoClaveEsIncorrecta() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("admin@plazoleta.com"))
                .thenReturn(Optional.of(adminValido));
        when(passwordEncoder.matches("clave1234", adminValido.getClave())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(loginRequest)
        );

        assertEquals("Credenciales incorrectas", exception.getMessage());
    }
}