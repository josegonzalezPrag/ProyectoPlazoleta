package com.pragma.microserviciousuario.domain.usercase;


import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import java.time.LocalDate;
import java.time.Period;

public class UsuarioUseCase implements IUsuarioServicio {
    private final IUsuarioRepositorio usuarioRepository;

    public UsuarioUseCase(IUsuarioRepositorio iUsuarioRepositorio) {
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
        return usuarioRepository.guardarUsuario(usuario);
    }

    @Override
    public Usuario obetenerUsuario(Long id) {
        return usuarioRepository.obetenerUsuario(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
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
