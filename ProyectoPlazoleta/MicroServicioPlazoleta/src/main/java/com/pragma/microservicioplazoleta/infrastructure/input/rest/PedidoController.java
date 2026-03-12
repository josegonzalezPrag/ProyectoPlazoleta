package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPedidoHandler;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
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
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {
    private final IPedidoHandler pedidoHandler;
    private final IRestauranteEmpleadoRespositorio restauranteEmpleadoRepositorio;

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
        Long idRestaurante = restauranteEmpleadoRepositorio
                .obtenerPorIdEmpleado(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("El empleado no pertenece a ningún restaurante"))
                .getIdRestaurante();

        return ResponseEntity.ok(pedidoHandler.listarPedidos(idRestaurante, estado, pagina, tamano));
    }

    @PatchMapping("/{idPedido}/asignar")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponse> asignarEmpleado(@PathVariable Long idPedido) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idEmpleado = (Long) auth.getCredentials();
        Long idRestaurante = restauranteEmpleadoRepositorio
                .obtenerPorIdEmpleado(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("El empleado no pertenece a ningún restaurante"))
                .getIdRestaurante();
        return ResponseEntity.ok(pedidoHandler.asignarEmpleado(idPedido, idEmpleado,idRestaurante));
    }

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
}
