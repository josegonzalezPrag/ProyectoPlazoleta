package com.pragma.microservicioplazoleta.infrastructure.out.feign;


import com.pragma.microservicioplazoleta.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microservicioplazoleta.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "trazabilidad", url = "${trazabilidad.service.url}", configuration =  FeignConfig.class)
public interface ITrazabilidadFeignClient {
    @PostMapping("/trazabilidad/registrar")
    void registrarCambioEstado(@RequestBody TrazabilidadRequest request);

    @GetMapping("/trazabilidad/pedido/{idPedido}")
    List<TrazabilidadResponse> obtenerTrazabilidadPedido(@PathVariable Long idPedido);
}