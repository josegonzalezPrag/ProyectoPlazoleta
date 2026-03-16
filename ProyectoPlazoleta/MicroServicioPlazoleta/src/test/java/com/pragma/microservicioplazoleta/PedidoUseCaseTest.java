package com.pragma.microservicioplazoleta;

import com.pragma.microservicioplazoleta.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.domain.spi.ITrazabilidadClient;
import com.pragma.microservicioplazoleta.domain.usercase.PedidoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.PeidoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.PedidoNoEncontradoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {
    @Mock
    private ITrazabilidadClient trazabilidadClient;

    @Mock
    private IPedidoRepositorio pedidoRepositorio;

    @Mock
    private IRestauranteEmpleadoRespositorio restauranteEmpleadoRepositorio;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedidoValido;
    private Pedido pedidoEnPreparacion;
    private Pedido pedidoListo;
    private RestauranteEmpleado restauranteEmpleado;

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

        pedidoEnPreparacion = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .idChef(2L)
                .estado(PeidoConstantes.ESTADO_EN_PREPARACION)
                .build();

        pedidoListo = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .idChef(2L)
                .estado(PeidoConstantes.ESTADO_LISTO)
                .codigoEntrega("1234")
                .build();

        restauranteEmpleado = RestauranteEmpleado.builder()
                .idEmpleado(2L)
                .idRestaurante(1L)
                .build();
    }

    @Test
    void deberiaCrearPedidoExitosamente() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(false);
        when(pedidoRepositorio.platosPerteneceARestaurante(any(), eq(1L))).thenReturn(true);
        when(pedidoRepositorio.guardarPedido(any())).thenReturn(pedidoValido);

        Pedido resultado = pedidoUseCase.crearPedido(pedidoValido);

        assertNotNull(resultado);
        assertEquals(PeidoConstantes.ESTADO_PENDIENTE, pedidoValido.getEstado());
        assertNotNull(pedidoValido.getFecha());
        verify(pedidoRepositorio, times(1)).guardarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteTienePedidoEnProceso() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(true);

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.crearPedido(pedidoValido));

        verify(pedidoRepositorio, never()).guardarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPlatosNoPertenecenAlRestaurante() {
        when(pedidoRepositorio.clienteTienePedidoEnProceso(1L)).thenReturn(false);
        when(pedidoRepositorio.platosPerteneceARestaurante(any(), eq(1L))).thenReturn(false);

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.crearPedido(pedidoValido));

        verify(pedidoRepositorio, never()).guardarPedido(any());
    }

    @Test
    void deberiaAsignarEmpleadoExitosamente() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoEnPreparacion));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.of(restauranteEmpleado));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(true);
        when(pedidoRepositorio.actualizarPedido(any())).thenReturn(pedidoEnPreparacion);

        Pedido resultado = pedidoUseCase.asignarEmpleado(1L, 2L);

        assertNotNull(resultado);
        assertEquals(PeidoConstantes.ESTADO_EN_PREPARACION, pedidoEnPreparacion.getEstado());
        assertEquals(2L, pedidoEnPreparacion.getIdChef());
        verify(pedidoRepositorio, times(1)).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoExiste() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.empty());

        assertThrows(PedidoNoEncontradoException.class,
                () -> pedidoUseCase.asignarEmpleado(1L, 2L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoEmpleadoSinRestaurante() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoEnPreparacion));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.empty());

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.asignarEmpleado(1L, 2L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoPerteneceAlRestaurante() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoEnPreparacion));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.of(restauranteEmpleado));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(false);

        assertThrows(SinPermisosException.class,
                () -> pedidoUseCase.asignarEmpleado(1L, 2L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaEntregarPedidoExitosamente() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoListo));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.of(restauranteEmpleado));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(true);
        when(pedidoRepositorio.actualizarPedido(any())).thenReturn(pedidoListo);

        Pedido resultado = pedidoUseCase.entregarPedido(1L, "1234", 2L);

        assertNotNull(resultado);
        assertEquals(PeidoConstantes.ESTADO_ENTREGADO, pedidoListo.getEstado());
        verify(pedidoRepositorio, times(1)).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoEstaListo() {
        pedidoListo.setEstado(PeidoConstantes.ESTADO_EN_PREPARACION);
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoListo));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.of(restauranteEmpleado));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(true);

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.entregarPedido(1L, "1234", 2L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoCodigoEsIncorrecto() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoListo));
        when(restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(2L)).thenReturn(Optional.of(restauranteEmpleado));
        when(pedidoRepositorio.pedidoPerteneceARestaurante(1L, 1L)).thenReturn(true);

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.entregarPedido(1L, "9999", 2L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaCancelarPedidoExitosamente() {
        Pedido pedidoPendiente = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estado(PeidoConstantes.ESTADO_PENDIENTE)
                .build();

        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoPendiente));
        when(pedidoRepositorio.actualizarPedido(any())).thenReturn(pedidoPendiente);

        Pedido resultado = pedidoUseCase.cancelarPedido(1L, 1L);

        assertNotNull(resultado);
        assertEquals(PeidoConstantes.ESTADO_CANCELADO, pedidoPendiente.getEstado());
        verify(pedidoRepositorio, times(1)).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteNoEsDuenoDeLPedido() {
        Pedido pedidoPendiente = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estado(PeidoConstantes.ESTADO_PENDIENTE)
                .build();

        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoPendiente));

        assertThrows(SinPermisosException.class,
                () -> pedidoUseCase.cancelarPedido(1L, 99L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoEstaPendienteAlCancelar() {
        Pedido pedidoEnPreparacionParaCancelar = Pedido.builder()
                .id(1L)
                .idCliente(1L)
                .idRestaurante(1L)
                .estado(PeidoConstantes.ESTADO_EN_PREPARACION)
                .build();

        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoEnPreparacionParaCancelar));

        assertThrows(DatoInvalidoException.class,
                () -> pedidoUseCase.cancelarPedido(1L, 1L));

        verify(pedidoRepositorio, never()).actualizarPedido(any());
    }


    @Test
    void deberiaObtenerTrazabilidadPedidoExitosamente() {
        TrazabilidadResponse response = new TrazabilidadResponse();
        response.setIdPedido(1L);
        response.setEstadoNuevo(PeidoConstantes.ESTADO_PENDIENTE);

        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoValido));
        when(trazabilidadClient.obtenerTrazabilidadPedido(1L)).thenReturn(List.of(response));

        List<TrazabilidadResponse> resultado = pedidoUseCase.obtenerTrazabilidadPedido(1L, 1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(trazabilidadClient, times(1)).obtenerTrazabilidadPedido(1L);
    }

    @Test
    void deberiaLanzarExcepcionCuandoClienteNoEsDuenioAlConsultarTrazabilidad() {
        when(pedidoRepositorio.obtenerPedidoPorId(1L)).thenReturn(Optional.of(pedidoValido));

        assertThrows(SinPermisosException.class,
                () -> pedidoUseCase.obtenerTrazabilidadPedido(1L, 99L));

        verify(trazabilidadClient, never()).obtenerTrazabilidadPedido(any());
    }

}
