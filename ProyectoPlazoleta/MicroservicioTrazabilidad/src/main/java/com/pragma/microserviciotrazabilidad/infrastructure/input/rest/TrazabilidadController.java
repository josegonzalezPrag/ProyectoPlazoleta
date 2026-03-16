package com.pragma.microserviciotrazabilidad.infrastructure.input.rest;

import com.pragma.microserviciotrazabilidad.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microserviciotrazabilidad.aplication.handler.ITrazabilidadHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trazabilidad")
@RequiredArgsConstructor
public class TrazabilidadController {
    private final ITrazabilidadHandler handler;

    @Operation(
            summary = "Registrar cambio de estado",
            description = "Registra un cambio de estado de un pedido. Endpoint interno llamado por MicroServicioPlazoleta.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cambio de estado registrado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/registrar")
    public ResponseEntity<TrazabilidadResponse> registrarCambioEstado(
            @Valid @RequestBody TrazabilidadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(handler.registrarCambioEstado(request));
    }

    @Operation(
            summary = "Obtener trazabilidad de pedido",
            description = "Obtiene el historial de cambios de estado de un pedido. Puede ser ejecutado por CLIENTE o PROPIETARIO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Historial obtenido exitosamente"),
                    @ApiResponse(responseCode = "404", description = "No existe trazabilidad para el pedido"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @GetMapping("/pedido/{idPedido}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROPIETARIO')")
    public ResponseEntity<List<TrazabilidadResponse>> obtenerTrazabilidadPedido(
            @PathVariable Long idPedido) {
        return ResponseEntity.ok(handler.obtenerTrazabilidadPedido(idPedido));
    }

    @Operation(
            summary = "Calcular tiempo de entrega",
            description = "Calcula el tiempo en minutos entre el estado Pendiente y Entregado de un pedido. Solo puede ser ejecutado por un PROPIETARIO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tiempo calculado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El pedido fue cancelado o aún no ha sido entregado"),
                    @ApiResponse(responseCode = "404", description = "No existe trazabilidad para el pedido"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @GetMapping("/pedido/{idPedido}/tiempo")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<String> calcularTiempoEntrega(@PathVariable Long idPedido) {
        long minutos = handler.calcularTiempoEntrega(idPedido);
        return ResponseEntity.ok("Tiempo de entrega: " + minutos + " minutos");
    }
}
