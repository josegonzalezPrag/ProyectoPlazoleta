package com.pragma.microserviciousuario.aplication.handler.impl;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.aplication.handler.IUsuarioHandler;
import com.pragma.microserviciousuario.aplication.mapper.UsuarioRequestMapper;
import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.model.Rol;
import com.pragma.microserviciousuario.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioHabdlerImpl implements IUsuarioHandler {

    private final IUsuarioServicio usuarioServicio;
    private final UsuarioRequestMapper usuarioRequestMapper;
    private final Rol rol = new Rol();

    @Override
    public UsuarioResponse crearPropietario(UsuarioRequest request) {
        Usuario usuario = usuarioRequestMapper.toUsuario(request);
        rol.setNombre("PROPIETARIO");
        usuario.setRol(rol);
        return usuarioRequestMapper.toResponse(usuarioServicio.crearPropietario(usuario));
    }

    public  UsuarioResponse obetenerUsuario(Long id){
        return usuarioRequestMapper.toResponse(usuarioServicio.obetenerUsuario(id));
    }


}
