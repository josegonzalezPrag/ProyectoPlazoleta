package com.pragma.microserviciousuario.infrastructure.out.feign;

import com.pragma.microserviciousuario.aplication.dto.request.RestauranteEmpleadoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="plazoleta",url="${plazoleta.url}")
public interface IPlazoletaClient {
    @PostMapping("/restaurante-empleado/asignar")
    void asignarEmpleado(@RequestBody RestauranteEmpleadoRequest request);
}
