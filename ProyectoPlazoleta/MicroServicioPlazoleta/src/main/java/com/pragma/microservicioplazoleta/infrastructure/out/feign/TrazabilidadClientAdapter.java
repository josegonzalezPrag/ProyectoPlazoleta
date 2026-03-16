package com.pragma.microservicioplazoleta.infrastructure.out.feign;

import com.pragma.microservicioplazoleta.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microservicioplazoleta.domain.spi.ITrazabilidadClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrazabilidadClientAdapter implements ITrazabilidadClient {
    private final ITrazabilidadFeignClient trazabilidadFeignClient;

    @Override
    public void registrarCambioEstado(TrazabilidadRequest request) {
        trazabilidadFeignClient.registrarCambioEstado(request);
    }

    @Override
    public List<TrazabilidadResponse> obtenerTrazabilidadPedido(Long idPedido) {
        return trazabilidadFeignClient.obtenerTrazabilidadPedido(idPedido);
    }
}