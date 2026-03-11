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

    @Override
    public UsuarioResponse crearPropietario(UsuarioRequest request) {
        Usuario usuario = usuarioRequestMapper.toUsuario(request);
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setNombre("PROPIETARIO");
        usuario.setRol(rol);
        return usuarioRequestMapper.toResponse(usuarioServicio.crearPropietario(usuario));
    }

    @Override
    public UsuarioResponse crearEmpleado(UsuarioRequest request) {
        Usuario usuario = usuarioRequestMapper.toUsuario(request);
        Rol rolEmpleado = new Rol();
        rolEmpleado.setId(3L);
        rolEmpleado.setNombre("EMPLEADO");
        usuario.setRol(rolEmpleado);
        return usuarioRequestMapper.toResponse(
                usuarioServicio.crearEmpleado(usuario, request.getIdRestaurante())
        );
    }

    @Override
    public UsuarioResponse crearCliente(UsuarioRequest request) {
        Usuario usuario = usuarioRequestMapper.toUsuario(request);
        Rol rolCliente = new Rol();
        rolCliente.setId(4L);
        rolCliente.setNombre("CLIENTE");
        usuario.setRol(rolCliente);
        return usuarioRequestMapper.toResponse(usuarioServicio.crearCliente(usuario));
    }

    @Override
    public  UsuarioResponse obetenerUsuario(Long id){
        return usuarioRequestMapper.toResponse(usuarioServicio.obetenerUsuario(id));
    }


}
