package com.pragma.microservicioplazoleta.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  {
        String cliente = "CLIENTE";
        String empleado = "EMPLEADO";
        String propietario = "PROPIETARIO";
        String administradot = "ADMINISTRATOR";
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/restaurante/crear").hasRole(administradot)
                        .requestMatchers(HttpMethod.GET, "/restaurante").hasRole(cliente)
                        .requestMatchers(HttpMethod.POST, "/plato/crear").hasRole(propietario)
                        .requestMatchers(HttpMethod.PATCH, "/plato/**").hasRole(propietario)
                        .requestMatchers(HttpMethod.GET, "/plato/restaurante/**").hasRole(cliente)
                        .requestMatchers(HttpMethod.POST, "/pedido/crear").hasRole(cliente)
                        .requestMatchers(HttpMethod.GET, "/pedido/listar").hasRole(empleado)
                        .requestMatchers(HttpMethod.PATCH, "/pedido/*/asignar").hasRole(empleado)
                        .requestMatchers(HttpMethod.GET, "/pedido/mis-pedidos").hasRole(cliente)
                        .requestMatchers(HttpMethod.POST, "/restaurante-empleado/asignar").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/pedido/*/listo").hasRole(empleado)
                        .requestMatchers(HttpMethod.PATCH, "/pedido/*/entregar").hasRole(empleado)
                        .requestMatchers(HttpMethod.PATCH, "/pedido/*/cancelar").hasRole(cliente)
                        .requestMatchers(HttpMethod.GET, "/pedido/*/trazabilidad").hasRole(cliente)
                        .anyRequest().permitAll()
                ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
