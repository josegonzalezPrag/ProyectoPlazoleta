package com.pragma.microserviciousuario.domain.usercase;


import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioUseCase implements IUsuarioServicio {
    private final IUsuarioRepositorio usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioUseCase(IUsuarioRepositorio iUsuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = iUsuarioRepositorio;
    }

    @Override
    public Usuario crearPropietario(Usuario usuario) {
        if (!esMayorDeEdad(usuario.getFechaNacimiento())) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad");
        }
        if (!celularValido(usuario.getCelular())) {
            throw new IllegalArgumentException("El celular debe tener máximo 13 caracteres");
        }
        if (!correoValido(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.guardarUsuario(usuario);
    }

    @Override
    public Usuario obetenerUsuario(Long id) {
        return usuarioRepository.obetenerUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public Usuario crearEmpleado(Usuario usuario) {
        if (!esMayorDeEdad(usuario.getFechaNacimiento())) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad");
        }
        if (!celularValido(usuario.getCelular())) {
            throw new IllegalArgumentException("El celular debe tener máximo 13 caracteres");
        }
        if (!correoValido(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        if (usuarioRepository.obtenerUsuarioPorCorreo(usuario.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.guardarUsuario(usuario);
    }

    @Override
    public Usuario crearCliente(Usuario usuario) {
        if (!celularValido(usuario.getCelular())) {
            throw new IllegalArgumentException("El celular debe tener máximo 13 caracteres");
        }
        if (!correoValido(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo no es válido");
        }
        if (usuarioRepository.obtenerUsuarioPorCorreo(usuario.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.guardarUsuario(usuario);
    }


    private boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    private boolean celularValido(String celular) {
        return celular.matches("^\\+?\\d{1,12}$");
    }

    private boolean correoValido(String correo) {
        return correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
