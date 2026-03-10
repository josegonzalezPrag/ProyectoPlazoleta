package com.pragma.microservicioplazoleta.aplication.mapper;


import com.pragma.microservicioplazoleta.aplication.dto.request.PlatoRequest;
import com.pragma.microservicioplazoleta.aplication.dto.response.PlatoResponse;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoRequestMapper {
    @Mapping(source = "idCategoria", target = "categoria.id")
    Plato toPlato(PlatoRequest request);

    PlatoResponse toResponse(Plato plato);
}
