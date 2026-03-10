package com.pragma.microserviciousuario.domain.spi;

import com.pragma.microserviciousuario.domain.model.Usuario;

import java.util.Optional;


public interface IUsuarioRepositorio {
    Usuario guardarUsuario(Usuario usuario);
    Optional<Usuario> obetenerUsuario(Long id);
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
}
