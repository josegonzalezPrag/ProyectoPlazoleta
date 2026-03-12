package com.pragma.microserviciousuario;

import com.pragma.microserviciousuario.domain.model.Rol;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.domain.usercase.UsuarioUseCase;
import com.pragma.microserviciousuario.infrastructure.out.feign.IPlazoletaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private IUsuarioRepositorio iUsuarioRepositorio;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IPlazoletaClient plazoletaClient;
    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        Rol rol = new Rol();
        rol.setNombre("PROPIETARIO");
        usuarioValido = Usuario.builder()
                .nombre("Carlos")
                .apellido("Ramirez")
                .documentoIdentidad(1003656701L)
                .celular("+573015678901")
                .fechaNacimiento(LocalDate.of(1990, 5, 15))
                .correo("carlos@gmail.com")
                .clave("clave1234")
                .rol(rol)
                .build();
    }

    @Test
    void deberiaCrearPropietarioExitosamente() {
        when(passwordEncoder.encode(any())).thenReturn("claveEncriptada");
        when(iUsuarioRepositorio.guardarUsuario(any())).thenReturn(usuarioValido);

        Usuario resultado = usuarioUseCase.crearPropietario(usuarioValido);

        assertNotNull(resultado);
        verify(iUsuarioRepositorio, times(1)).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEsMenorDeEdad() {
        usuarioValido.setFechaNacimiento(LocalDate.of(2015, 1, 1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido)
        );

        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCelularExcede13Caracteres() {
        usuarioValido.setCelular("+5730156789012345");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido)
        );

        assertEquals("El celular debe tener máximo 13 caracteres", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoEsInvalido() {
        usuarioValido.setCorreo("correo-invalido");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido)
        );

        assertEquals("El correo no es válido", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaCrearEmpleadoExitosamente() {
        usuarioValido.getRol().setNombre("EMPLEADO");

        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("claveEncriptada");
        when(iUsuarioRepositorio.guardarUsuario(any())).thenReturn(usuarioValido);
        doNothing().when(plazoletaClient).asignarEmpleado(any());

        Usuario resultado = usuarioUseCase.crearEmpleado(usuarioValido, 1L);

        assertNotNull(resultado);
        verify(iUsuarioRepositorio, times(1)).guardarUsuario(any());
        verify(plazoletaClient, times(1)).asignarEmpleado(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoYaExisteEnEmpleado() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.of(usuarioValido));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L)
        );

        assertEquals("Ya existe un usuario con ese correo", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmpleadoEsMenorDeEdad() {
        usuarioValido.setFechaNacimiento(LocalDate.of(2015, 1, 1));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L)
        );

        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCelularInvalidoEnEmpleado() {
        usuarioValido.setCelular("+5730156789012345");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L)
        );

        assertEquals("El celular debe tener máximo 13 caracteres", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoInvalidoEnEmpleado() {
        usuarioValido.setCorreo("correo-invalido");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L)
        );

        assertEquals("El correo no es válido", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }
    @Test
    void deberiaCrearClienteExitosamente() {
        usuarioValido.getRol().setNombre("CLIENTE");

        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("claveEncriptada");
        when(iUsuarioRepositorio.guardarUsuario(any())).thenReturn(usuarioValido);

        Usuario resultado = usuarioUseCase.crearCliente(usuarioValido);

        assertNotNull(resultado);
        verify(iUsuarioRepositorio, times(1)).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoYaExisteEnCliente() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.of(usuarioValido));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearCliente(usuarioValido)
        );

        assertEquals("Ya existe un usuario con ese correo", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCelularInvalidoEnCliente() {
        usuarioValido.setCelular("+5730156789012345");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearCliente(usuarioValido)
        );

        assertEquals("El celular debe tener máximo 13 caracteres", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoInvalidoEnCliente() {
        usuarioValido.setCorreo("correo-invalido");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioUseCase.crearCliente(usuarioValido)
        );

        assertEquals("El correo no es válido", exception.getMessage());
        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }
    }