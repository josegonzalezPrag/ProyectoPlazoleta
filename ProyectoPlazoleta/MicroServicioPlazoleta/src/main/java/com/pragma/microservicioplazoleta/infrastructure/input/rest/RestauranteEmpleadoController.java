package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteEmpleadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteEmpleadoHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante-empleado")
@RequiredArgsConstructor
public class RestauranteEmpleadoController {
    private final IRestauranteEmpleadoHandler handler;

    @PostMapping("/asignar")
    public ResponseEntity<RestauranteEmpleadoResponse> asignarEmpleado(
            @Valid @RequestBody RestauranteEmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(handler.asignarEmpleado(request));
    }
}
