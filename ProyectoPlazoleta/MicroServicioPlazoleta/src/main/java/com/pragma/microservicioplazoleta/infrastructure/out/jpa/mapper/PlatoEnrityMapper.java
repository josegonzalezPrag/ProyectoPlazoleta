package com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper;

import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PlatoEntiy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoEnrityMapper {
    @Mapping(target = "categoria", ignore = true)
    PlatoEntiy toEntidy(Plato plato);
    Plato toModel(PlatoEntiy platoEntiy);
}
