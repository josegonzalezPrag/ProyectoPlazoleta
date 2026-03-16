package com.pragma.microserviciotrazabilidad;

import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoEmpleadoResponse;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TiempoPedidoResponse;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import com.pragma.microserviciotrazabilidad.domain.spi.ITrazabilidadRepositorio;
import com.pragma.microserviciotrazabilidad.domain.usercase.TrazabilidadUseCase;
import com.pragma.microserviciotrazabilidad.domain.usercase.constantes.TrazabilidadConstantes;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoCanceladoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.PedidoNoEntregadoException;
import com.pragma.microserviciotrazabilidad.infrastructure.exceptionhandler.excepciones.TrazabilidadNoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TrazabilidadUseCaseTest {

    @Mock
    private ITrazabilidadRepositorio repositorio;

    @InjectMocks
    private TrazabilidadUseCase trazabilidadUseCase;

    private List<Trazabilidad> registrosRestaurante1;

    private Trazabilidad trazabilidadPendiente;
    private Trazabilidad trazabilidadEnPreparacion;
    private Trazabilidad trazabilidadEntregado;
    private Trazabilidad trazabilidadCancelado;

    @BeforeEach
    void setUp() {
        registrosRestaurante1 = new ArrayList<>();

        trazabilidadPendiente = Trazabilidad.builder()
                .idPedido(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estadoAnterior(null)
                .estadoNuevo("Pendiente")
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 0))
                .build();

        trazabilidadEnPreparacion = Trazabilidad.builder()
                .idPedido(1L)
                .idCliente(1L)
                .idEmpleado(2L)
                .idRestaurante(1L)
                .estadoAnterior("Pendiente")
                .estadoNuevo("En_Preparacion")
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 15))
                .build();

        trazabilidadEntregado = Trazabilidad.builder()
                .idPedido(1L)
                .idCliente(1L)
                .idEmpleado(2L)
                .idRestaurante(1L)
                .estadoAnterior("En_Preparacion")
                .estadoNuevo("Entregado")
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 45))
                .build();

        trazabilidadCancelado = Trazabilidad.builder()
                .idPedido(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estadoAnterior("Pendiente")
                .estadoNuevo("Cancelado")
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 10))
                .build();

        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(1L).idCliente(1L).idRestaurante(1L).idEmpleado(null)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_PENDIENTE)
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 0)).build());
        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(1L).idCliente(1L).idRestaurante(1L).idEmpleado(1L)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_EN_PREPARACION)
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 15)).build());
        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(1L).idCliente(1L).idRestaurante(1L).idEmpleado(1L)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_ENTREGADO)
                .fechaCambio(LocalDateTime.of(2024, 1, 1, 10, 45)).build());

        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(2L).idCliente(1L).idRestaurante(1L).idEmpleado(null)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_PENDIENTE)
                .fechaCambio(LocalDateTime.of(2024, 1, 2, 9, 0)).build());
        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(2L).idCliente(1L).idRestaurante(1L).idEmpleado(1L)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_EN_PREPARACION)
                .fechaCambio(LocalDateTime.of(2024, 1, 2, 9, 20)).build());
        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(2L).idCliente(1L).idRestaurante(1L).idEmpleado(1L)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_ENTREGADO)
                .fechaCambio(LocalDateTime.of(2024, 1, 2, 10, 0)).build());

        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(3L).idCliente(1L).idRestaurante(1L).idEmpleado(null)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_PENDIENTE)
                .fechaCambio(LocalDateTime.of(2024, 1, 3, 8, 0)).build());
        registrosRestaurante1.add(Trazabilidad.builder()
                .idPedido(3L).idCliente(1L).idRestaurante(1L).idEmpleado(null)
                .estadoNuevo(TrazabilidadConstantes.ESTADO_CANCELADO)
                .fechaCambio(LocalDateTime.of(2024, 1, 3, 8, 5)).build());
    }

    @Test
    void deberiaCalcularTiempoEntregaExitosamente() {
        when(repositorio.obtenerPorPedido(1L)).thenReturn(
                List.of(trazabilidadPendiente, trazabilidadEnPreparacion, trazabilidadEntregado));

        long minutos = trazabilidadUseCase.calcularTiempoEntrega(1L);

        assertEquals(45, minutos);
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoCancelado() {
        when(repositorio.obtenerPorPedido(1L)).thenReturn(
                List.of(trazabilidadPendiente, trazabilidadCancelado));

        assertThrows(PedidoCanceladoException.class,
                () -> trazabilidadUseCase.calcularTiempoEntrega(1L));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoEntregado() {
        when(repositorio.obtenerPorPedido(1L)).thenReturn(
                List.of(trazabilidadPendiente, trazabilidadEnPreparacion));

        assertThrows(PedidoNoEntregadoException.class,
                () -> trazabilidadUseCase.calcularTiempoEntrega(1L));
    }

    @Test
    void deberiaObtenerTrazabilidadPedidoExitosamente() {
        when(repositorio.obtenerPorPedido(1L)).thenReturn(
                List.of(trazabilidadPendiente, trazabilidadEnPreparacion, trazabilidadEntregado));

        List<Trazabilidad> resultado = trazabilidadUseCase.obtenerTrazabilidadPedido(1L);

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        verify(repositorio, times(1)).obtenerPorPedido(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoExisteTrazabilidad() {
        when(repositorio.obtenerPorPedido(1L)).thenReturn(List.of());

        assertThrows(TrazabilidadNoEncontradaException.class,
                () -> trazabilidadUseCase.obtenerTrazabilidadPedido(1L));
    }

    @Test
    void deberiaRegistrarCambioEstadoExitosamente() {
        when(repositorio.guardarTrazabilidad(any())).thenReturn(trazabilidadPendiente);

        Trazabilidad resultado = trazabilidadUseCase.registrarCambioEstado(trazabilidadPendiente);

        assertNotNull(resultado);
        assertNotNull(trazabilidadPendiente.getFechaCambio());
        verify(repositorio, times(1)).guardarTrazabilidad(any());
    }

    @Test
    void deberiaObtenerEficienciaSoloConPedidosEntregados() {
        when(repositorio.obtenerPorRestaurante(1L)).thenReturn(registrosRestaurante1);

        List<TiempoPedidoResponse> resultado = trazabilidadUseCase.obtenerEficienciaPorPedido(1L);

        assertEquals(2, resultado.size());
        assertFalse(resultado.stream().anyMatch(r -> r.getIdPedido().equals(3L)));
    }

    @Test
    void deberiaCalcularTiempoCorrectoPorPedido() {
        when(repositorio.obtenerPorRestaurante(1L)).thenReturn(registrosRestaurante1);

        List<TiempoPedidoResponse> resultado = trazabilidadUseCase.obtenerEficienciaPorPedido(1L);

        TiempoPedidoResponse pedido1 = resultado.stream()
                .filter(r -> r.getIdPedido().equals(1L))
                .findFirst().orElseThrow();
        assertEquals(45, pedido1.getMinutosEntrega());
    }

    @Test
    void deberiaCalcularTiempoMedioYOrdenarRankingAscendente() {
        when(repositorio.obtenerPorRestaurante(1L)).thenReturn(registrosRestaurante1);

        List<TiempoEmpleadoResponse> resultado = trazabilidadUseCase.obtenerRankingPorEmpleado(1L);

        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.getFirst().getIdEmpleado());
        assertEquals(52.5, resultado.getFirst().getTiempoMedioMinutos());
    }
}
