package com.pragma.microserviciousuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServicioUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServicioUsuarioApplication.class, args);
    }

}
