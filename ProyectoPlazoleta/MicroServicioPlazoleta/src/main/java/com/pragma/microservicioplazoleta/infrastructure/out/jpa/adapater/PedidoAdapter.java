package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.spi.IPedidoRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PedidoEntity;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.PedidoEntityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.PedidoRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.PlatoPedidoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PedidoAdapter implements IPedidoRepositorio {
    private final PedidoRepositorio pedidoRepositorio;
    private final PlatoPedidoRepositorio platoRepositorio;
    private final PedidoEntityMapper pedidoEntityMapper;

    @Override
    public Pedido guardarPedido(Pedido pedido) {
        PedidoEntity entity = pedidoEntityMapper.toEntity(pedido);
        if (entity.getPlatos() != null) {
            entity.getPlatos().forEach(p -> p.setPedido(entity));
        }
        return pedidoEntityMapper.toModel(pedidoRepositorio.save(entity));
    }

    @Override
    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepositorio.findById(id)
                .map(pedidoEntityMapper::toModel);
    }

    @Override
    public boolean clienteTienePedidoEnProceso(Long idCliente) {
        return pedidoRepositorio.existsByIdClienteAndEstadoIn(
                idCliente,
                List.of("Pendiente", "En_Preparacion", "Listo")
        );
    }

    @Override
    public boolean platosPerteneceARestaurante(List<Long> idPlatos, Long idRestaurante) {
        return idPlatos.stream()
                .allMatch(idPlato -> platoRepositorio.existsByIdAndIdRestaurante(idPlato, idRestaurante));
    }

    @Override
    public List<Pedido> listarPedidosPorRestauranteYEstado(Long idRestaurante, String estado, int pagina, int tamano) {
        List<PedidoEntity> entities = pedidoRepositorio.findByIdRestauranteAndEstado(idRestaurante, estado);

        int inicio = pagina * tamano;
        int fin = Math.min(inicio + tamano, entities.size());

        if (inicio >= entities.size()) return List.of();

        return entities.subList(inicio, fin)
                .stream()
                .map(pedidoEntityMapper::toModel)
                .toList();
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(Long idCliente) {
        return pedidoRepositorio.findByIdCliente(idCliente)
                .stream()
                .map(pedidoEntityMapper::toModel)
                .toList();
    }

    @Override
    public Pedido pedidoEnPreparacion(Pedido pedido) {
        PedidoEntity entity = pedidoRepositorio.findById(pedido.getId())
                .orElseThrow(() -> new IllegalArgumentException("El pedido no existe"));
        entity.setEstado(pedido.getEstado());
        entity.setIdChef(pedido.getIdChef());
        return pedidoEntityMapper.toModel(pedidoRepositorio.save(entity));
    }

    @Override
    public boolean pedidoPerteneceARestaurante(Long idPedido, Long idRestaurante) {
        return pedidoRepositorio.existsByIdAndIdRestaurante(idPedido, idRestaurante);
    }

}
