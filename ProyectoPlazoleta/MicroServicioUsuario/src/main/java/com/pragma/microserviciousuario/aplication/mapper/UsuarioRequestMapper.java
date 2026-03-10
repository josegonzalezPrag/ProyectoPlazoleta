package com.pragma.microserviciousuario.aplication.mapper;

import com.pragma.microserviciousuario.aplication.dto.request.UsuarioRequest;
import com.pragma.microserviciousuario.aplication.dto.response.UsuarioResponse;
import com.pragma.microserviciousuario.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsuarioRequestMapper {
    Usuario toUsuario(UsuarioRequest request);

    @Mapping(source = "rol.nombre", target = "rolNombre")
    UsuarioResponse toResponse(Usuario usuario);
}
