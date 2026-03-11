package com.pragma.microservicioplazoleta.infrastructure.configuration;


import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteEmpleadoServicio;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.spi.*;
import com.pragma.microservicioplazoleta.domain.usercase.PlatoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteEmpleadoUseCase;
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

    @Bean
    public IPlatoServicio platoServicio(IPlatoRepositorio iPlatoRepositorio,
                                        IRestaurantePlatoRepositorio iRestauranteRepositorioPlato,
                                        IUsuarioClient iUsuarioClient) {
        return new PlatoUseCase(iPlatoRepositorio, iRestauranteRepositorioPlato, iUsuarioClient);
    }

    @Bean
    public IRestauranteEmpleadoServicio restauranteEmpleadoServicio(
            IRestauranteEmpleadoRespositorio repositorio,
            IRestaurantePlatoRepositorio restauranteRepositorio,
            IUsuarioClient usuarioClient) {
        return new RestauranteEmpleadoUseCase(repositorio, restauranteRepositorio, usuarioClient);
    }


}
