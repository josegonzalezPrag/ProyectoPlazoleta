package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPlatoHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {
    private final IPlatoHandler platoHandler;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<PlatoResponse> crearPlato(@Valid @RequestBody PlatoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platoHandler.crearPlato(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<PlatoResponse> actualizarPlato(@PathVariable Long id,
                                                         @Valid @RequestBody PlatoUptadeRequest request) {
        return ResponseEntity.ok(platoHandler.actualizarPlato(id, request));
    }

}
