package com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper;

import com.pragma.microservicioplazoleta.domain.model.Pedido;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoEntityMapper {
    PedidoEntity toEntity(Pedido pedido);
    Pedido toModel(PedidoEntity entity);
}
