package com.pragma.microserviciousuario.infrastructure.out.jpa.adapater;

import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.RolEntity;
import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.UsuarioEntiy;
import com.pragma.microserviciousuario.infrastructure.out.jpa.mapper.UsuarioEntiyMapper;
import com.pragma.microserviciousuario.infrastructure.out.jpa.repository.RolRepositorio;
import com.pragma.microserviciousuario.infrastructure.out.jpa.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UsuarioAdapter implements IUsuarioRepositorio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioEntiyMapper usuarioEntiyMapper;
    private final RolRepositorio rolRepositorio;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        UsuarioEntiy entity = usuarioEntiyMapper.toEntity(usuario);

        RolEntity rolEntity = rolRepositorio.findByNombre(usuario.getRol().getNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + usuario.getRol().getNombre()));
        entity.setRol(rolEntity);

        return usuarioEntiyMapper.toModel(usuarioRepositorio.save(entity));
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
