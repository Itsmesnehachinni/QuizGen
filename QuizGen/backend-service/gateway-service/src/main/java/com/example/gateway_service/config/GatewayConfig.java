package com.example.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("user-service", r -> r.path("/v1/user-service/**")
                        .uri("http://localhost:8081"))
                .route("pharma-service", r -> r.path("/v1/pharma-service/**")
                        .uri("http://localhost:8082"))
                .route("order-service", r -> r.path("/v1/order-service/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
