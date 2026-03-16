package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoUptadeRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPlatoHandler;
import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoEstadoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Platos", description = "Gestión de platos del sistema")
@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {
    private final IPlatoHandler platoHandler;

    @Operation(
            summary = "Crear plato",
            description = "Crea un nuevo plato en el restaurante. PROPIETARIO.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @PostMapping("/crear")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<PlatoResponse> crearPlato(@Valid @RequestBody PlatoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platoHandler.crearPlato(request));
    }

    @Operation(
            summary = "Actualizar plato",
            description = "Actualiza el precio y descripción de un plato. PROPIETARIO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plato actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción"),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<PlatoResponse> actualizarPlato(@PathVariable Long id,
                                                         @Valid @RequestBody PlatoUptadeRequest request) {
        return ResponseEntity.ok(platoHandler.actualizarPlato(id, request));
    }

    @Operation(
            summary = "Cambiar estado del plato",
            description = "Activa o desactiva un plato. PROPIETARIO del mismo restaurante.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estado del plato actualizado exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción"),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")
            }
    )
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

    @Operation(
            summary = "Listar platos por restaurante",
            description = "Lista los platos de un restaurante con un filtro por categoría. CLIENTE.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de platos obtenida exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
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
