package com.pragma.microservicioplazoleta.infrastructure.configuration;


import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.spi.IRestauranteRepositorio;
import com.pragma.microservicioplazoleta.domain.spi.IUsuarioClient;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public IRestauranteServicio restauranteServicio(IRestauranteRepositorio iRestauranteRepositorio,
                                                    IUsuarioClient iUsuarioClient) {
        return new RestauranteUseCase(iRestauranteRepositorio, iUsuarioClient);
    }


}
