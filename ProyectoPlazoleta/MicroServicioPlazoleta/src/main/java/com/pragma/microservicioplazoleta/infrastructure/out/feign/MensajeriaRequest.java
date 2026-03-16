package com.pragma.microservicioplazoleta.infrastructure.out.feign;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MensajeriaRequest  {
    private String numeroCelular;
    private String mensaje;
}
