package com.pragma.microservicioplazoleta.infrastructure.input.rest;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {
    private final IRestauranteHandler restauranteHandler;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RestauranteResponse> crearRestaurante(@Valid @RequestBody RestauranteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteHandler.crearRestaurante(request));
    }
}
