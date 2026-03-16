package com.pragma.microserviciomensajeria.domain.api;

public interface IMensajeriaService {
    void enviarSms(String numeroCelular, String mensaje);

}
