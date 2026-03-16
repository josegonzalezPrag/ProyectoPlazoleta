package com.pragma.microserviciotrazabilidad.infrastructure.configuration;



import com.pragma.microserviciotrazabilidad.domain.api.ITrazabilidadServicio;
import com.pragma.microserviciotrazabilidad.domain.spi.ITrazabilidadRepositorio;
import com.pragma.microserviciotrazabilidad.domain.usercase.TrazabilidadUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ITrazabilidadServicio trazabilidadServicio(ITrazabilidadRepositorio repositorio) {
        return new TrazabilidadUseCase(repositorio);
    }


}
