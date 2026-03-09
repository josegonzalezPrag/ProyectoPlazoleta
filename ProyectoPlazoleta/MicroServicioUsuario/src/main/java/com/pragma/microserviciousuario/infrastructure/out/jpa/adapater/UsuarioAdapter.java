package com.pragma.microserviciousuario.infrastructure.out.jpa.adapater;

import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.UsuarioEntiy;
import com.pragma.microserviciousuario.infrastructure.out.jpa.mapper.UsuarioEntiyMapper;
import com.pragma.microserviciousuario.infrastructure.out.jpa.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


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

    @Override
    public Optional<Usuario> obetenerUsuario(Long id) {
        Optional<UsuarioEntiy> entity = usuarioRepositorio.findById(id);
        return entity.map(usuarioEntiyMapper::toModel);
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo)
                .map(usuarioEntiyMapper::toModel);
    }

}
