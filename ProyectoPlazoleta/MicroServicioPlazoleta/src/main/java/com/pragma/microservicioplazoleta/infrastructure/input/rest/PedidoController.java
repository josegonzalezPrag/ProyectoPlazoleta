package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IPedidoHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {
    private final IPedidoHandler pedidoHandler;

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
}
