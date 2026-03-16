package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPedidoHandler;
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

@Tag(name = "Pedidos", description = "Gestión de pedidos del sistema")
@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {
    private final IPedidoHandler pedidoHandler;

    @Operation(
            summary = "Crear pedido",
            description = "Crea un nuevo pedido. CLIENTE.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido ceado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválids o cliente ya tiene un pedido en proceso"),
                    @ApiResponse(responseCode = "403", description = "No tiene permsos para realizar esta acción")
            }
    )
    @PostMapping("/crear")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody PedidoRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long idCliente = (Long) auth.getCredentials();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoHandler.crearPedido(request, idCliente));
    }

    @Operation(
            summary = "Listar pedidos por estado",
            description = "Lista los pedidos del restaurante del empleado autenticado filtrando. EMPLEADO.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @GetMapping("/listar")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<List<PedidoResponse>> listarPedidos(
            @RequestParam String estado,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idEmpleado = (Long) auth.getCredentials();
        return ResponseEntity.ok(pedidoHandler.listarPedidos(idEmpleado, estado, pagina, tamano));
    }

    @Operation(
            summary = "Asignar empleado a pedido",
            description = "Asigna el empleado autenticado a un pedido y lo pasa a En_Preparacion. EMPLEADO del mismo restaurante.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empleado asignado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El pedido no pertenece al restaurante del empleado"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @PatchMapping("/{idPedido}/asignar")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponse> asignarEmpleado(@PathVariable Long idPedido) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idEmpleado = (Long) auth.getCredentials();
        return ResponseEntity.ok(pedidoHandler.asignarEmpleado(idPedido, idEmpleado));
    }

    @Operation(
            summary = "Listar mis pedidos",
            description = "Lista todos los pedios del cliente autenticado.  CLIENTE.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @GetMapping("/mis-pedidos")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<PedidoResponse>> listarMisPedidos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idCliente = (Long) auth.getCredentials();
        return ResponseEntity.ok(pedidoHandler.listarPedidosPorCliente(idCliente));
    }

    @Operation(
            summary = "Marcar pedido como listo",
            description = "Marca un pedido como Listo y envía el código de entrega al cliente. EMPLEADO con Pedido en preparacion.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido marcado como listo exitosamente"),
                    @ApiResponse(responseCode = "400", description = "El pedido no está En_Preparacion o el empleado no es el chef asignado"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @PatchMapping("/{idPedido}/listo")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponse> marcarComoListo(@PathVariable Long idPedido) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idChef = (Long) auth.getCredentials();
        return ResponseEntity.ok(pedidoHandler.marcarComoListo(idPedido, idChef));
    }
}
