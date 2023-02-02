//package by.minilooth.apigateway.configuration;
//
//import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.Buildable;
//import org.springframework.cloud.gateway.route.builder.PredicateSpec;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfiguration {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
////                .route(r -> r.path("/providers/**")
////                        .filters(f -> f.rewritePath("/providers(?<segment>/?.*)", "/api/$\\{segment}"))
////                        .uri("lb://provider-service"))
////                .route(r -> r.path("/vehicles/**")
////                        .filters(f -> f.rewritePath("/vehicles(?<segment>/?.*)", "/api/$\\{segment}"))
////                        .uri("lb://vehicle-service"))
//                .route("vehicles_route", r -> r.path("/vehicles/**")
//                        .filters(f -> f.rewritePath("/vehicles/(?<segment>/?.*)", "/$\\{segment}"))
//                        .uri("lb://vehicle-service"))
//                .route("swagger", r -> r.path("/v3/api-docs/**")
//                        .filters(f -> f.rewritePath("/v3/api-docs/(?<path>.*)", "/$\\{path}/v3/api-docs"))
//                        .uri("http://localhost:8080"))
//                .build();
//    }
//
//}
