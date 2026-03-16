package com.pragma.microserviciomensajeria.infrastructure.input.rest;

import com.pragma.microserviciomensajeria.aplication.dto.request.MensajeRequet;
import com.pragma.microserviciomensajeria.infrastructure.configuration.TwilioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensajeria")
@RequiredArgsConstructor
public class MensaheController {
    private final TwilioService twilioService;

    @Operation(
            summary = "Enviar SMS",
            description = "Envía un mensaje SMS al número indicado. Endpoint interno llamado por MicroServicioPlazoleta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Mensaje enviado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/enviar")
    public ResponseEntity<String> enviarMensaje(@Valid @RequestBody MensajeRequet request) {
        twilioService.enviarSms(request.getNumeroCelular(), request.getMensaje());
        return ResponseEntity.ok("Mensaje enviado exitosamente");
    }
}
