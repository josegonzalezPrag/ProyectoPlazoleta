package com.pragma.microservicioplazoleta.infrastructure.out.feign;


import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioClientAdapter implements IUsuarioClient {
    private final IUsuarioFeugbClient usuarioFeignClient;

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioFeignClient.obtenerUsuarioPorId(id);
    }
}
