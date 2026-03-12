package com.pragma.microservicioplazoleta.aplication.mapper;

import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoPlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.request.PedidoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoPlatoResponse;
import com.pragma.microservicioplazoleta.aplication.dto.response.PedidoResponse;
import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoRequestMapper {
    @Mapping(source = "idPlato", target = "idPlato")
    PedidoPlato toPedidoPlato(PedidoPlatoRequest request);
    Pedido toPedido(PedidoRequest request);
    PedidoPlatoResponse toPedidoPlatoResponse(PedidoPlato pedidoPlato);
    PedidoResponse toResponse(Pedido pedido);
}
