package com.pragma.microservicioplazoleta.infrastructure.configuration;


import com.pragma.microservicioplazoleta.domain.api.IPedidoServicio;
import com.pragma.microservicioplazoleta.domain.api.IPlatoServicio;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteEmpleadoServicio;
import com.pragma.microservicioplazoleta.domain.api.IRestauranteServicio;
import com.pragma.microservicioplazoleta.domain.spi.*;
import com.pragma.microservicioplazoleta.domain.usercase.PedidoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.PlatoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteEmpleadoUseCase;
import com.pragma.microservicioplazoleta.domain.usercase.RestauranteUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public IRestauranteServicio restauranteServicio(IRestauranteRepositorio restauranteRepositorio,
                                                    IUsuarioClient usuarioClient,
                                                    ITrazabilidadClient trazabilidadClient) {
        return new RestauranteUseCase(restauranteRepositorio, usuarioClient, trazabilidadClient);
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

    @Bean
    public IPedidoServicio pedidoServicio(IPedidoRepositorio pedidoRepositorio,
                                          IUsuarioClient usuarioClient,
                                          IRestauranteEmpleadoRespositorio restauranteEmpleadoRepositorio,
                                          IMensajeriClientt mensajeriaClient,
                                          ITrazabilidadClient trazabilidadClient) {
        return new PedidoUseCase(pedidoRepositorio, usuarioClient, restauranteEmpleadoRepositorio, mensajeriaClient, trazabilidadClient);
    }


}
