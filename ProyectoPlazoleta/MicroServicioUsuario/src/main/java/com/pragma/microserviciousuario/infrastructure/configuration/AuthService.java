package com.pragma.microserviciousuario.infrastructure.configuration;

import com.pragma.microserviciousuario.aplication.dto.request.LoginRequest;
import com.pragma.microserviciousuario.aplication.dto.response.LoginResponse;
import com.pragma.microserviciousuario.domain.model.Usuario;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUsuarioRepositorio iUsuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final JwTConfig jwtConfig;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = iUsuarioRepositorio.obtenerUsuarioPorCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getClave(), usuario.getClave())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        String token = jwtConfig.generarToken(usuario.getCorreo(), usuario.getRol().getNombre(),usuario.getId());
        return new LoginResponse(token);
    }
}
