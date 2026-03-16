package com.pragma.microserviciomensajeria.infrastructure.configuration;

import com.pragma.microserviciomensajeria.domain.api.IMensajeriaService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioService implements IMensajeriaService {
    private final TwilioConfig twilioConfig;

    @Override
    public void enviarSms(String numeroCelular, String mensaje) {
        crearMensaje(numeroCelular, mensaje);
    }

    public void crearMensaje(String numeroCelular, String mensaje) {
        Message.creator(
                new PhoneNumber(numeroCelular),
                new PhoneNumber(twilioConfig.getPhoneNumber()),
                mensaje
        ).create();
    }
}
