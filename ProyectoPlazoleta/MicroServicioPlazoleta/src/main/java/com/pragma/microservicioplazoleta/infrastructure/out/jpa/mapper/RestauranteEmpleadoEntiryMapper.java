package com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper;

import com.pragma.microservicioplazoleta.domain.model.RestauranteEmpleado;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestauranteEmpleadoEntiryMapper {
    RestauranteEmpleadoEntity toEntity(RestauranteEmpleado restauranteEmpleado);
    RestauranteEmpleado toModel(RestauranteEmpleadoEntity entity);
}
