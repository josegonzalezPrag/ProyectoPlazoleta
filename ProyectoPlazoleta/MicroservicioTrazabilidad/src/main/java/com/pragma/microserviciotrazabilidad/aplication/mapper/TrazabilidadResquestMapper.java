package com.pragma.microserviciotrazabilidad.aplication.mapper;

import com.pragma.microserviciotrazabilidad.aplication.dto.request.TrazabilidadRequest;
import com.pragma.microserviciotrazabilidad.aplication.dto.response.TrazabilidadResponse;
import com.pragma.microserviciotrazabilidad.domain.model.Trazabilidad;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TrazabilidadResquestMapper {
    Trazabilidad toModel(TrazabilidadRequest request);
    TrazabilidadResponse toResponse(Trazabilidad trazabilidad);
}
