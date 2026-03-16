package com.pragma.microserviciousuario.domain.usercase;


import com.pragma.microserviciousuario.aplication.dto.request.RestauranteEmpleadoRequest;
import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.model.Rol;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.domain.usercase.constants.UsuarioConstantes;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.DatoInvalidoException;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.UsuarioMenordeEdadException;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.UsuarioNoEncontradoException;
import com.pragma.microserviciousuario.infrastructure.exceptionhandler.exceptions.UsuarioYaExisteException;
import com.pragma.microserviciousuario.infrastructure.out.feign.IPlazoletaClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioUseCase implements IUsuarioServicio {
    private final IUsuarioRepositorio usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final IPlazoletaClient plazoletaClient;

    public UsuarioUseCase(IUsuarioRepositorio iUsuarioRepositorio, PasswordEncoder passwordEncoder, IPlazoletaClient plazoletaClient) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = iUsuarioRepositorio;
        this.plazoletaClient = plazoletaClient;
    }

    private void validarDatosBasicos(Usuario usuario) {
        if (usuario.getFechaNacimiento() == null ||
                Period.between(usuario.getFechaNacimiento(), LocalDate.now()).getYears() < 18) {
            throw new UsuarioMenordeEdadException(UsuarioConstantes.USUARIO_MENOR_DE_EDAD);
        }
        if (usuario.getCelular() == null || usuario.getCelular().length() > 13) {
            throw new DatoInvalidoException(UsuarioConstantes.CELULAR_INVALIDO);
        }
        if (usuario.getCorreo() == null || !usuario.getCorreo().matches(UsuarioConstantes.REGEX_CORREO)) {
            throw new DatoInvalidoException(UsuarioConstantes.CORREO_INVALIDO);
        }
        if (usuarioRepository.obtenerUsuarioPorCorreo(usuario.getCorreo()).isPresent()) {
            throw new UsuarioYaExisteException(UsuarioConstantes.CORREO_YA_EXISTE);
        }
        if (!usuarioRepository.rolExiste(usuario.getRol().getId())) {
            throw new DatoInvalidoException(UsuarioConstantes.ROL_NO_ENCONTRADO);
        }
    }

    @Override
    public Usuario crearCliente(Usuario usuario) {
        Rol rol = new Rol();
        rol.setId(UsuarioConstantes.ROL_ID_CLIENTE);
        rol.setNombre(UsuarioConstantes.ROL_CLIENTE);
        usuario.setRol(rol);
        validarDatosBasicos(usuario);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.guardarUsuario(usuario);
    }

    @Override
    public Usuario crearPropietario(Usuario usuario) {
        Rol rol = new Rol();
        rol.setId(UsuarioConstantes.ROL_ID_PROPIETARIO);
        rol.setNombre(UsuarioConstantes.ROL_PROPIETARIO);
        usuario.setRol(rol);
        validarDatosBasicos(usuario);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.guardarUsuario(usuario);
    }

    @Override
    public Usuario obetenerUsuario(Long id) {
        return usuarioRepository.obetenerUsuario(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException(UsuarioConstantes.USUARIO_NO_ENCONTRADO));
    }

    @Override
    public Usuario crearEmpleado(Usuario usuario, Long idRestaurante) {
        Rol rol = new Rol();
        rol.setId(UsuarioConstantes.ROL_ID_EMPLEADO);
        rol.setNombre(UsuarioConstantes.ROL_EMPLEADO);
        usuario.setRol(rol);
        validarDatosBasicos(usuario);
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        Usuario guardado = usuarioRepository.guardarUsuario(usuario);

        RestauranteEmpleadoRequest relacion = new RestauranteEmpleadoRequest();
        relacion.setIdEmpleado(guardado.getId());
        relacion.setIdRestaurante(idRestaurante);
        plazoletaClient.asignarEmpleado(relacion);

        return guardado;
    }

}
