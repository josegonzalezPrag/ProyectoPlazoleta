package com.pragma.microserviciotrazabilidad.domain.usercase;

import com.pragma.microserviciotrazabilidad.domain.api.ITrazabilidadServicio;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import com.pragma.microserviciotrazabilidad.domain.spi.ITrazabilidadRepositorio;
import com.pragma.microserviciotrazabilidad.domain.usercase.constantes.TrazabilidadConstantes;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoCanceladoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoNoEntregadoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.TrazabilidadNoEncontradaException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TrazabilidadUseCase implements ITrazabilidadServicio {
    private final ITrazabilidadRepositorio repositorio;

    public TrazabilidadUseCase(ITrazabilidadRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Trazabilidad registrarCambioEstado(Trazabilidad trazabilidad) {
        trazabilidad.setFechaCambio(LocalDateTime.now());
        return repositorio.guardarTrazabilidad(trazabilidad);
    }

    @Override
    public List<Trazabilidad> obtenerTrazabilidadPedido(Long idPedido) {
        List<Trazabilidad> trazabilidad = repositorio.obtenerPorPedido(idPedido);
        if (trazabilidad.isEmpty()) {
            throw new TrazabilidadNoEncontradaException(TrazabilidadConstantes.TRAZABILIDAD_NO_ENCONTRADA + idPedido);
        }
        return trazabilidad;
    }

    @Override
    public long calcularTiempoEntrega(Long idPedido) {
        List<Trazabilidad> trazabilidad = repositorio.obtenerPorPedido(idPedido);

        if (trazabilidad.isEmpty()) {
            throw new TrazabilidadNoEncontradaException(TrazabilidadConstantes.TRAZABILIDAD_NO_ENCONTRADA + idPedido);
        }

        boolean cancelado = trazabilidad.stream()
                .anyMatch(t -> TrazabilidadConstantes.ESTADO_CANCELADO.equals(t.getEstadoNuevo()));
        if (cancelado) {
            throw new PedidoCanceladoException(TrazabilidadConstantes.PEDIDO_CANCELADO);
        }

        Trazabilidad inicio = trazabilidad.stream()
                .filter(t -> TrazabilidadConstantes.ESTADO_PENDIENTE.equals(t.getEstadoNuevo()))
                .findFirst()
                .orElseThrow(() -> new TrazabilidadNoEncontradaException(TrazabilidadConstantes.ESTADO_PENDIENTE_NO_ENCONTRADO));

        Trazabilidad fin = trazabilidad.stream()
                .filter(t -> TrazabilidadConstantes.ESTADO_ENTREGADO.equals(t.getEstadoNuevo()))
                .findFirst()
                .orElseThrow(() -> new PedidoNoEntregadoException(TrazabilidadConstantes.PEDIDO_NO_ENTREGADO));

        return ChronoUnit.MINUTES.between(inicio.getFechaCambio(), fin.getFechaCambio());
    }
}
