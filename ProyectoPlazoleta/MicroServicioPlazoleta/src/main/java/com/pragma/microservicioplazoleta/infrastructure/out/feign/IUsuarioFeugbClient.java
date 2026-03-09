package com.pragma.microservicioplazoleta.infrastructure.out.feign;



import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios", url = "${usuario.service.url}",configuration = FeignConfig.class)
public interface IUsuarioFeugbClient {

    @GetMapping("/usuario/{id}")
    Usuario obtenerUsuarioPorId(@PathVariable Long id);
}
