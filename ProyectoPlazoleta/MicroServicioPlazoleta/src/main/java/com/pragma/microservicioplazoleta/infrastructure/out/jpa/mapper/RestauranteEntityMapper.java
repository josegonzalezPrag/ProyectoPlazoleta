package com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper;


import com.pragma.microservicioplazoleta.domain.model.Restaurante;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestaurabteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestauranteEntityMapper {
    RestaurabteEntity toEntity(Restaurante restaurante);
    Restaurante toModel(RestaurabteEntity restauranteEntity);
}
