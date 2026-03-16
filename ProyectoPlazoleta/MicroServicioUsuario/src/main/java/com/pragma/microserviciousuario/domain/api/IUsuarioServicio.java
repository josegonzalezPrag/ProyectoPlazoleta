package com.pragma.microserviciousuario.domain.api;


import com.pragma.microserviciousuario.domain.model.Usuario;

public interface IUsuarioServicio {
    Usuario crearPropietario(Usuario usuario);
    Usuario crearCliente(Usuario usuario);
    Usuario crearEmpleado(Usuario usuario, Long idRestaurante);
    Usuario obetenerUsuario(Long id);
}
