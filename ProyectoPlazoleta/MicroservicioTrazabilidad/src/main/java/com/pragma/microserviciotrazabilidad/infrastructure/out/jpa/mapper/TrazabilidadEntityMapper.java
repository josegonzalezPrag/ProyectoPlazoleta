package com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.mapper;

import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import com.pragma.microserviciotrazabilidad.infrastructure.out.jpa.entity.TrazabilidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TrazabilidadEntityMapper {
    TrazabilidadEntity toEntity(Trazabilidad trazabilidad);
    Trazabilidad toModel(TrazabilidadEntity entity);
}
