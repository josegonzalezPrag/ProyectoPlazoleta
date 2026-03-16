package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.spi.IMensajeriClientt;
import com.pragma.microservicioplazoleta.infrastructure.out.feign.IMensajeriaFeignClient;
import com.pragma.microservicioplazoleta.infrastructure.out.feign.MensajeriaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MensajeriaClientAdpater implements IMensajeriClientt {
    private final IMensajeriaFeignClient mensajeriaFeignClient;

    @Override
    public void enviarSms(String numeroCelular, String mensaje) {
        MensajeriaRequest request = new MensajeriaRequest();
        request.setNumeroCelular(numeroCelular);
        request.setMensaje(mensaje);
        mensajeriaFeignClient.enviarSms(request);
    }
}
