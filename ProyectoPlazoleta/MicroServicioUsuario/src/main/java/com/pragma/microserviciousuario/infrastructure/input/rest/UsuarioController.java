package com.pragma.microserviciousuario.infrastructure.input.rest;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.aplication.handler.IUsuarioHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final IUsuarioHandler usuarioHandler;

    @PostMapping("/propietario")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponse> crearPropietario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioHandler.crearPropietario(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obetenerUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioHandler.obetenerUsuario(id));
    }

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
