package com.pragma.microservicioplazoleta.infrastructure.input.rest;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<RestauranteResponse>> listarRestaurantes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return ResponseEntity.ok(restauranteHandler.listarRestaurantes(pagina, tamano));
    }
}
