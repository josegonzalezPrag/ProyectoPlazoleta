package com.pragma.microserviciotrazabilidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioTrazabilidadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioTrazabilidadApplication.class, args);
    }

}
