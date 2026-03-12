package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;
import com.pragma.microservicioplazoleta.domain.usercase.PedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoEmpleadoTest {
    @Mock
    private IPedidoRepositorio pedidoRepositorio;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estado("Pendiente")
                .build();
    }

    @Test
    void deberiaAsignarEmpleadoExitosamente() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(true);
        when(pedidoRepositorio.pedidoEnPreparacion(any())).thenReturn(pedido);

        Pedido resultado = pedidoUseCase.asignarEmpleado(1L, 2L, 1L);

        assertNotNull(resultado);
        assertEquals("En_Preparacion", pedido.getEstado());
        assertEquals(2L, pedido.getIdChef());
        verify(pedidoRepositorio, times(1)).pedidoEnPreparacion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoExiste() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoUseCase.asignarEmpleado(1L, 2L, 1L)
        );

        assertEquals("El pedido no existe", exception.getMessage());
        verify(pedidoRepositorio, never()).pedidoEnPreparacion(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoperteneceAlRestaurante() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 2L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoUseCase.asignarEmpleado(1L, 2L, 2L)
        );

        assertEquals("El pedido no pertenece al restaurante del empleado", exception.getMessage());
        verify(pedidoRepositorio, never()).pedidoEnPreparacion(any());
    }
}
