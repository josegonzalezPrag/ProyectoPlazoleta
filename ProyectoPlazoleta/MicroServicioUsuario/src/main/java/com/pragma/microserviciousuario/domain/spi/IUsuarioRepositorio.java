package com.pragma.microserviciousuario.domain.spi;

import com.pragma.microserviciousuario.domain.model.Usuario;


public interface IUsuarioRepositorio {
    Usuario guardarUsuario(Usuario usuario);
}
