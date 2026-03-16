package com.pragma.microserviciousuario.infrastructure.input.rest;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.aplication.handler.IUsuarioHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final IUsuarioHandler usuarioHandler;

    @Operation(
            summary = "Crear propietario",
            description = "Crea un nuevo usuario con rol PROPIETARIO. Solo puede ser ejecutado por un ADMINISTRADOR.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "403", description = "No tiene permisos para realizar esta acción")
            }
    )
    @PostMapping("/propietario")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponse> crearPropietario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearPropietario(request));
    }

    @Operation(
            summary = "Obtener usuario",
            description = "Obtiene un usuario por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obetenerUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioHandler.obetenerUsuario(id));
    }

    @Operation(
            summary = "Crear cliente",
            description = "Crea un nuevo usuario con rol CLIENTE. Endpoint público.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado")
            }
    )
    @PostMapping("/empleado")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<UsuarioResponse> crearEmpleado(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearEmpleado(request));
    }

    @PostMapping("/cliente")
    public ResponseEntity<UsuarioResponse> crearCliente(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearCliente(request));
    }

}
