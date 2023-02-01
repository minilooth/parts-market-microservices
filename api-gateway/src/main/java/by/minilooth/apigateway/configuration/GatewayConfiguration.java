package by.minilooth.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/provider/**")
                        .uri("lb://PROVIDER-SERVICE"))
                .route(r -> r.path("/api/v1/vehicle/**")
                        .uri("lb://VEHICLE-SERVICE"))
                .route(r -> r.path("/api/v1/make/**")
                        .uri("lb://VEHICLE-SERVICE"))
                .route(r -> r.path("/api/v1/model/**")
                        .uri("lb://VEHICLE-SERVICE"))
                .route(r -> r.path("/api/v1/generation/**")
                        .uri("lb://VEHICLE-SERVICE"))
                .build();
    }

}
