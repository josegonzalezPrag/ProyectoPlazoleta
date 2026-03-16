package com.pragma.microservicioplazoleta.domain.spi;

public interface IMensajeriClientt {
    void enviarSms(String numeroCelular, String mensaje);
}