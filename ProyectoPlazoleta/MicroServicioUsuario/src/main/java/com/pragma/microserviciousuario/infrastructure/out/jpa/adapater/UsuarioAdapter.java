package com.pragma.microserviciousuario.infrastructure.out.jpa.adapater;

import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.infrastructure.out.jpa.mapper.UsuarioEntiyMapper;
import com.pragma.microserviciousuario.infrastructure.out.jpa.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UsuarioAdapter implements IUsuarioRepositorio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioEntiyMapper usuarioEntiyMapper;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioEntiyMapper.toModel(
                usuarioRepositorio.save(usuarioEntiyMapper.toEntity(usuario))
        );
    }



}
