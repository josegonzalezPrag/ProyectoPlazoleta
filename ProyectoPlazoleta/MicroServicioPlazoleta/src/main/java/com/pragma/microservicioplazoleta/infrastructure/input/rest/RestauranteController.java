package com.pragma.microservicioplazoleta.infrastructure.input.rest;


import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteHandler;
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

@Tag(name = "Restaurantes", description = "Gestión de restaurantes del sistema")
@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {
    private final IRestauranteHandler restauranteHandler;

    @Operation(
            summary = "Crear restaurante",
            description = "Crea un nuevo restaurante.  ADMINISTRADOR.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<RestauranteResponse> crearRestaurante(@Valid @RequestBody RestauranteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteHandler.crearRestaurante(request));
    }

    @Operation(
            summary = "Listar restaurantes",
            description = "Lista todos los restaurantes de forma pagnada. CLIENTE.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de restaurantes obtenida exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<RestauranteResponse>> listarRestaurantes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        return ResponseEntity.ok(restauranteHandler.listarRestaurantes(pagina, tamano));
    }

    @Operation(
            summary = "Obtener eficiencia por pedido",
            description = "Muestra el tiempo de entrega de cada pedido del restaurante. Solo puede ser ejecutado por el PROPIETARIO del restaurante.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eficiencia obtenida exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tienes permiso para ver este restaurante"),
                    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
            }
    )
    @GetMapping("/{idRestaurante}/eficiencia")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<List<TiempoPedidoResponse>> obtenerEficienciaPorPedido(@PathVariable Long idRestaurante) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idPropietario = (Long) auth.getCredentials();
        return ResponseEntity.ok(restauranteHandler.obtenerEficienciaPorPedido(idRestaurante, idPropietario));
    }

    @Operation(
            summary = "Obtener ranking de empleados",
            description = "Muestra el ranking de empleados por tiempo medio de entrega. Solo puede ser ejecutado por el PROPIETARIO del restaurante.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ranking obtenido exitosamente"),
                    @ApiResponse(responseCode = "403", description = "No tienes permiso para ver este restaurante"),
                    @ApiResponse(responseCode = "404", description = "Restaurante no encontrado")
            }
    )
    @GetMapping("/{idRestaurante}/ranking-empleados")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<List<TiempoEmpleadoResponse>> obtenerRankingPorEmpleado(@PathVariable Long idRestaurante) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getCredentials() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long idPropietario = (Long) auth.getCredentials();
        return ResponseEntity.ok(restauranteHandler.obtenerRankingPorEmpleado(idRestaurante, idPropietario));
    }
}
