package com.pragma.microservicioplazoleta.infrastructure.input.rest;

import com.pragma.microservicioplazoleta.aplication.dto.request.RestauranteEmpleadoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.RestauranteEmpleadoResponse;
import com.pragma.microservicioplazoleta.aplication.handler.IRestauranteEmpleadoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Restaurante-Empleado", description = "Gestión de relación entre restaurantes y empleados")
@RestController
@RequestMapping("/restaurante-empleado")
@RequiredArgsConstructor
public class RestauranteEmpleadoController {
    private final IRestauranteEmpleadoHandler handler;

    @Operation(
            summary = "Asignar empleado",
            description = "Crea la relación entre un empleado y un restaurante",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Relación creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/asignar")
    public ResponseEntity<RestauranteEmpleadoResponse> asignarEmpleado(
            @Valid @RequestBody RestauranteEmpleadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(handler.asignarEmpleado(request));
    }
}
