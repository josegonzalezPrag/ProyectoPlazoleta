package com.pragma.microservicioplazoleta.domain.spi;


import com.pragma.microservicioplazoleta.domain.model.Usuario;

public interface IUsuarioClient {
    Usuario obtenerUsuarioPorId(Long id);
}
