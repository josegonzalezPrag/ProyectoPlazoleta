package com.pragma.microservicioplazoleta.domain.usercase;

import com.pragma.microservicioplazoleta.domain.api.IPedidoServicio;
import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import com.pragma.microservicioplazoleta.domain.model.Usuario;
import com.pragma.microservicioplazoleta.domain.spi.IMensajeriClientt;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteEmpleadoRespositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.constantes.PeidoConstantes;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.PedidoNoEncontradoException;
import com.pragma.microservicioplazoleta.infrastructure.exceptionhandler.exceptions.SinPermisosException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class PedidoUseCase implements IPedidoServicio {
    private final IPedidoRepositorio pedidoRepositorio;
    private final IUsuarioClient usuarioClient;
    private final IRestauranteEmpleadoRespositorio restauranteEmpleadoRepositorio;
    private final IMensajeriClientt mensajeriaClient;
    private final Random r = new Random();

    public PedidoUseCase(IPedidoRepositorio pedidoRepositorio,
                         IUsuarioClient usuarioClient,
                         IRestauranteEmpleadoRespositorio restauranteEmpleadoRepositorio,
                         IMensajeriClientt mensajeriaClient) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.usuarioClient = usuarioClient;
        this.restauranteEmpleadoRepositorio = restauranteEmpleadoRepositorio;
        this.mensajeriaClient = mensajeriaClient;
    }

    private Pedido obtenerPedidoOLanzarExcepcion(Long idPedido) {
        return pedidoRepositorio.obtenerPedidoPorId(idPedido)
                .orElseThrow(() -> new PedidoNoEncontradoException(PeidoConstantes.PEDIDO_NO_EXISTE));
    }

    @Override
    public Pedido crearPedido(Pedido pedido) {
        if (pedidoRepositorio.clienteTienePedidoEnProceso(pedido.getIdCliente()))
            throw new DatoInvalidoException(PeidoConstantes.CLIENTE_CON_PEDIDO);

        List<Long> idPlatos = pedido.getPlatos().stream()
                .map(PedidoPlato::getIdPlato)
                .toList();

        if (!pedidoRepositorio.platosPerteneceARestaurante(idPlatos, pedido.getIdRestaurante()))
            throw new DatoInvalidoException(PeidoConstantes.PLATOS_RESTAURANTE_INVALIDO);

        pedido.setEstado(PeidoConstantes.ESTADO_PENDIENTE);
        pedido.setFecha(LocalDateTime.now());
        return pedidoRepositorio.guardarPedido(pedido);
    }


    @Override
    public List<Pedido> listarPedidos(Long idEmpleado, String estado, int pagina, int tamano) {
        Long idRestaurante = restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(idEmpleado)
                .orElseThrow(() -> new DatoInvalidoException(PeidoConstantes.EMPLEADO_SIN_RESTAURANTE))
                .getIdRestaurante();
        return pedidoRepositorio.listarPedidosPorRestauranteYEstado(idRestaurante, estado, pagina, tamano);
    }

    @Override
    public Pedido asignarEmpleado(Long idPedido, Long idEmpleado) {
        Pedido pedido = obtenerPedidoOLanzarExcepcion(idPedido);

        Long idRestaurante = restauranteEmpleadoRepositorio.obtenerPorIdEmpleado(idEmpleado)
                .orElseThrow(() -> new DatoInvalidoException(PeidoConstantes.EMPLEADO_SIN_RESTAURANTE))
                .getIdRestaurante();

        if (!pedidoRepositorio.pedidoPerteneceARestaurante(idPedido, idRestaurante))
            throw new SinPermisosException(PeidoConstantes.PEDIDO_RESTAURANTE_INVALIDO);

        pedido.setEstado(PeidoConstantes.ESTADO_EN_PREPARACION);
        pedido.setIdChef(idEmpleado);
        return pedidoRepositorio.actualizarPedido(pedido);
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(Long idCliente) {
        return pedidoRepositorio.listarPedidosPorCliente(idCliente);
    }

    @Override
    public Pedido marcarComoListo(Long idPedido, Long idChef) {
        Pedido pedido = obtenerPedidoOLanzarExcepcion(idPedido);

        if (!pedido.getIdChef().equals(idChef))
            throw new SinPermisosException(PeidoConstantes.CHEF_NO_ASIGNADO);
        if (!PeidoConstantes.ESTADO_EN_PREPARACION.equals(pedido.getEstado()))
            throw new DatoInvalidoException(PeidoConstantes.ESTADO_INVALIDO);

        String codigo = String.valueOf(r.nextInt(9000) + 1000);
        Usuario cliente = usuarioClient.obtenerUsuarioPorId(pedido.getIdCliente());
        mensajeriaClient.enviarSms(cliente.getCelular(), codigo);

        pedido.setEstado(PeidoConstantes.ESTADO_LISTO);
        pedido.setCodigoEntrega(codigo);
        return pedidoRepositorio.actualizarPedido(pedido);
    }
}
