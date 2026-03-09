package com.pragma.microserviciousuario.aplication.handler;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;

public interface IUsuarioHandler {
    UsuarioResponse crearPropietario(UsuarioRequest request);
    UsuarioResponse obetenerUsuario(Long id);
}
