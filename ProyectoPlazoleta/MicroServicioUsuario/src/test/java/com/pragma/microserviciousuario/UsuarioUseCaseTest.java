package com.pragma.microserviciousuario;

import com.pragma.microserviciousuario.domain.model.Rol;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.domain.usercase.UsuarioUseCase;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.UsuarioMenordeEdadException;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.UsuarioYaExisteException;
import com.pragma.microserviciousuario.infrastructure.out.feign.IPlazoletaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private IUsuarioRepositorio iUsuarioRepositorio;

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
    void deberiaLanzarExcepcionCuandoEsMenorDeEdad() {
        usuarioValido.setFechaNacimiento(LocalDate.of(2015, 1, 1));

        assertThrows(UsuarioMenordeEdadException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCelularExcede13Caracteres() {
        usuarioValido.setCelular("+5730156789012345");

        assertThrows(DatoInvalidoException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoEsInvalido() {
        usuarioValido.setCorreo("correo-invalido");

        assertThrows(DatoInvalidoException.class,
                () -> usuarioUseCase.crearPropietario(usuarioValido));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoYaExisteEnEmpleado() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.of(usuarioValido));

        assertThrows(UsuarioYaExisteException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmpleadoEsMenorDeEdad() {
        usuarioValido.setFechaNacimiento(LocalDate.of(2015, 1, 1));

        assertThrows(UsuarioMenordeEdadException.class,
                () -> usuarioUseCase.crearEmpleado(usuarioValido, 1L));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
        verify(plazoletaClient, never()).asignarEmpleado(any());
    }


    @Test
    void deberiaLanzarExcepcionCuandoCorreoYaExisteEnCliente() {
        when(iUsuarioRepositorio.obtenerUsuarioPorCorreo("carlos@gmail.com")).thenReturn(Optional.of(usuarioValido));

        assertThrows(UsuarioYaExisteException.class,
                () -> usuarioUseCase.crearCliente(usuarioValido));

        verify(iUsuarioRepositorio, never()).guardarUsuario(any());
    }

    }