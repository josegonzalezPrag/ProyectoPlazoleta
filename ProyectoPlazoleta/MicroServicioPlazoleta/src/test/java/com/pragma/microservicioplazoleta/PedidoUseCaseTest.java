package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;
import com.pragma.microservicioplazoleta.domain.usercase.PedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {
    @Mock
    private IPedidoRepositorio pedidoRepositorio;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedidoValido;

    @BeforeEach
    void setUp() {
        List<PedidoPlato> platos = List.of(
                PedidoPlato.builder().idPlato(1L).cantidad(2).build(),
                PedidoPlato.builder().idPlato(2L).cantidad(1).build()
        );

        pedidoValido = Pedido.builder()
                .idCliente(1L)
                .idRestaurante(1L)
                .platos(platos)
                .build();
    }

    @Test
    void deberiaCrearPedidoExitosamente() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(false);
        when(pedidoRepositorio.platosPerteneceARestaurante(any(), eq(1L))).thenReturn(true);
        when(pedidoRepositorio.guardarPedido(any())).thenReturn(pedidoValido);

        Pedido resultado = pedidoUseCase.crearPedido(pedidoValido);

        assertNotNull(resultado);
        assertEquals("Pendiente", pedidoValido.getEstado());
        assertNotNull(pedidoValido.getFecha());
        verify(pedidoRepositorio, times(1)).guardarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteTienePedidoEnProceso() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoUseCase.crearPedido(pedidoValido)
        );

        assertEquals("El cliente ya tiene un pedido en proceso", exception.getMessage());
        verify(pedidoRepositorio, never()).guardarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatosNoPertenecenAlRestaurante() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(false);
        when(pedidoRepositorio.platosPerteneceARestaurante(any(), eq(1L))).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoUseCase.crearPedido(pedidoValido)
        );

        assertEquals("Todos los platos deben pertenecer al mismo restaurante", exception.getMessage());
        verify(pedidoRepositorio, never()).guardarPedido(any());
    }

    @Test
    void deberiaSetearEstadoPendienteAlCrear() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(false);
        when(pedidoRepositorio.platosPerteneceARestaurante(any(), eq(1L))).thenReturn(true);
        when(pedidoRepositorio.guardarPedido(any())).thenReturn(pedidoValido);

        pedidoUseCase.crearPedido(pedidoValido);

        assertEquals("Pendiente", pedidoValido.getEstado());
        assertNotNull(pedidoValido.getFecha());
    }
}
