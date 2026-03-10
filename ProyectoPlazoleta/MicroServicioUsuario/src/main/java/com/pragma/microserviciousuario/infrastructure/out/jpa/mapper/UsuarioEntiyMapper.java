package com.pragma.microserviciousuario.infrastructure.out.jpa.mapper;

import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.UsuarioEntiy;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface UsuarioEntiyMapper {
    @Mapping(target = "rol", ignore = true)
    UsuarioEntiy toEntity(Usuario usuario);

    Usuario toModel(UsuarioEntiy usuarioEntiy);

    @Mapping(source = "rol.nombre", target = "rolNombre")
    UsuarioResponse toResponse(UsuarioEntiy usuarioEntiy);
}
