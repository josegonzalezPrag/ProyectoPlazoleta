package com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper;

import com.pragma.microservicioplazoleta.domain.model.PedidoPlato;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PedidoPlatoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoEntityMapper {
    PedidoPlatoEntity toEntity(PedidoPlato pedidoPlato);
    PedidoPlato toModel(PedidoPlatoEntity entity);
}
