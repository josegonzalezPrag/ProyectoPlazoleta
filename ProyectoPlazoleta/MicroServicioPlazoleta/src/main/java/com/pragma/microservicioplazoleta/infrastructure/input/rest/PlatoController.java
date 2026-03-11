package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPlatoHandler;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoEstadoRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<PlatoResponse> cambiarEstadoPlato(
            @PathVariable Long id,
            @Valid @RequestBody PlatoEstadoRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long idPropietario = (Long) auth.getCredentials();

        return ResponseEntity.ok(platoHandler.cambiarEstadoPlato(id, request, idPropietario));
    }

    @GetMapping("/restaurante/{idRestaurante}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<PlatoResponse>> listarPlatosPorRestaurante(
            @PathVariable Long idRestaurante,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return ResponseEntity.ok(platoHandler.listarPlatosPorRestaurante(idRestaurante, idCategoria, pagina, tamano));
    }

}
