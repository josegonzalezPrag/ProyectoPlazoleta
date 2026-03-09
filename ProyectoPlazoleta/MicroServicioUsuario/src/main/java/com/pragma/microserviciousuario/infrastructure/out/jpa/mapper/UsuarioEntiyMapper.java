package com.pragma.microserviciousuario.infrastructure.out.jpa.mapper;

import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.infrastructure.out.jpa.entity.UsuarioEntiy;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface UsuarioEntiyMapper {
    UsuarioEntiy toEntity(Usuario usuario);
    Usuario toModel(UsuarioEntiy usuarioEntiy);
}
