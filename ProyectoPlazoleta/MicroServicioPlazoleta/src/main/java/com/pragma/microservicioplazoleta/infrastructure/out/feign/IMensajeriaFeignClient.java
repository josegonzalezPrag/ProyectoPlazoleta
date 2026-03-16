package com.pragma.microservicioplazoleta.infrastructure.out.feign;

import com.pragma.microservicioplazoleta.infrastructure.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mensajeria", url = "${mensajeria.service.url}", configuration = FeignConfig.class)
public interface IMensajeriaFeignClient {
    @PostMapping("/mensajeria/enviar")
    void enviarSms(@RequestBody MensajeriaRequest request);
}
