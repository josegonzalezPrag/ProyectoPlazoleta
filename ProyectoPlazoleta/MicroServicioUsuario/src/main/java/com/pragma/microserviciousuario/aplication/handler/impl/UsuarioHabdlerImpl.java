package com.pragma.microserviciousuario.aplication.handler.impl;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.aplication.handler.IUsuarioHandler;
import com.pragma.microserviciousuario.aplication.mapper.UsuarioRequestMapper;
import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.model.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioHabdlerImpl implements IUsuarioHandler {

    private final IUsuarioServicio usuarioServicio;
    private final UsuarioRequestMapper usuarioRequestMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse crearPropietario(UsuarioRequest request) {
        var usuario = usuarioRequestMapper.toUsuario(request);
        usuario.setClave(passwordEncoder.encode(request.getClave()));
        usuario.setRol(Rol.PROPIETARIO);
        var guardado = usuarioServicio.crearPropietario(usuario);
        return usuarioRequestMapper.toResponse(guardado);
    }

    public  UsuarioResponse obetenerUsuario(Long id){
        return usuarioRequestMapper.toResponse(usuarioServicio.obetenerUsuario(id));
    }


}
