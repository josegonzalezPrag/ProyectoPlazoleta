package com.pragma.microserviciousuario.infrastructure.configuration;

import com.pragma.microserviciousuario.domain.api.IUsuarioServicio;
import com.pragma.microserviciousuario.domain.spi.IUsuarioRepositorio;
import com.pragma.microserviciousuario.domain.usercase.UsuarioUseCase;
import com.pragma.microserviciousuario.infrastructure.out.feign.IPlazoletaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUsuarioServicio usuarioServicio(IUsuarioRepositorio iUsuarioRepositorio,
                                            PasswordEncoder passwordEncoder,
                                            IPlazoletaClient plazoletaClient) {
        return new UsuarioUseCase(iUsuarioRepositorio, passwordEncoder, plazoletaClient);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
