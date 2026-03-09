package com.pragma.microservicioplazoleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroServicioPlazoletaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServicioPlazoletaApplication.class, args);
    }

}
