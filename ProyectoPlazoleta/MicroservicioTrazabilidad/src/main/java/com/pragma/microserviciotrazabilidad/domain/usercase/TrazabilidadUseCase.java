package com.pragma.microserviciotrazabilidad.domain.usercase;

import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microserviciotrazabilidad.domain.api.ITrazabilidadServicio;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import com.pragma.microserviciotrazabilidad.domain.spi.ITrazabilidadRepositorio;
import com.pragma.microserviciotrazabilidad.domain.usercase.constantes.TrazabilidadConstantes;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoCanceladoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoNoEntregadoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.TrazabilidadNoEncontradaException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public List<TiempoPedidoResponse> obtenerEficienciaPorPedido(Long idRestaurante) {
        List<Trazabilidad> registros = repositorio.obtenerPorRestaurante(idRestaurante);

        Map<Long, List<Trazabilidad>> porPedido = registros.stream()
                .collect(Collectors.groupingBy(Trazabilidad::getIdPedido));

        return porPedido.entrySet().stream()
                .filter(entry -> {
                    List<Trazabilidad> lista = entry.getValue();
                    boolean tienePendiente = lista.stream().anyMatch(t -> TrazabilidadConstantes.ESTADO_PENDIENTE.equals(t.getEstadoNuevo()));
                    boolean tieneEntregado = lista.stream().anyMatch(t -> TrazabilidadConstantes.ESTADO_ENTREGADO.equals(t.getEstadoNuevo()));
                    return tienePendiente && tieneEntregado;
                })
                .map(entry -> {
                    List<Trazabilidad> lista = entry.getValue();
                    LocalDateTime inicio = lista.stream()
                            .filter(t -> TrazabilidadConstantes.ESTADO_PENDIENTE.equals(t.getEstadoNuevo()))
                            .findFirst().get().getFechaCambio();
                    LocalDateTime fin = lista.stream()
                            .filter(t -> TrazabilidadConstantes.ESTADO_ENTREGADO.equals(t.getEstadoNuevo()))
                            .findFirst().get().getFechaCambio();
                    return TiempoPedidoResponse.builder()
                            .idPedido(entry.getKey())
                            .minutosEntrega(ChronoUnit.MINUTES.between(inicio, fin))
                            .build();
                })
                .toList();
    }

    @Override
    public List<TiempoEmpleadoResponse> obtenerRankingPorEmpleado(Long idRestaurante) {
        List<Trazabilidad> registros = repositorio.obtenerPorRestaurante(idRestaurante);

        Map<Long, List<Trazabilidad>> porPedido = registros.stream()
                .collect(Collectors.groupingBy(Trazabilidad::getIdPedido));

        Map<Long, List<Long>> tiemposPorEmpleado = new HashMap<>();

        porPedido.forEach((idPedido, lista) -> {
            boolean tienePendiente = lista.stream().anyMatch(t -> TrazabilidadConstantes.ESTADO_PENDIENTE.equals(t.getEstadoNuevo()));
            boolean tieneEntregado = lista.stream().anyMatch(t -> TrazabilidadConstantes.ESTADO_ENTREGADO.equals(t.getEstadoNuevo()));

            if (tienePendiente && tieneEntregado) {
                LocalDateTime inicio = lista.stream()
                        .filter(t -> TrazabilidadConstantes.ESTADO_PENDIENTE.equals(t.getEstadoNuevo()))
                        .findFirst().get().getFechaCambio();
                LocalDateTime fin = lista.stream()
                        .filter(t -> TrazabilidadConstantes.ESTADO_ENTREGADO.equals(t.getEstadoNuevo()))
                        .findFirst().get().getFechaCambio();

                long minutos = ChronoUnit.MINUTES.between(inicio, fin);

                lista.stream()
                        .filter(t -> TrazabilidadConstantes.ESTADO_EN_PREPARACION.equals(t.getEstadoNuevo()) && t.getIdEmpleado() != null)
                        .findFirst()
                        .ifPresent(t -> tiemposPorEmpleado
                                .computeIfAbsent(t.getIdEmpleado(), k -> new ArrayList<>())
                                .add(minutos));
            }
        });

        return tiemposPorEmpleado.entrySet().stream()
                .map(entry -> TiempoEmpleadoResponse.builder()
                        .idEmpleado(entry.getKey())
                        .tiempoMedioMinutos(entry.getValue().stream()
                                .mapToLong(Long::longValue)
                                .average()
                                .orElse(0))
                        .build())
                .sorted(Comparator.comparingDouble(TiempoEmpleadoResponse::getTiempoMedioMinutos))
                .toList();
    }
}
