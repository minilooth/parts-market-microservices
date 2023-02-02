package by.minilooth.apigateway.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class DocumentationConfiguration {

    @Autowired private RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (Objects.isNull(definitions)) {
            return Collections.emptyList();
        }
        return definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .map(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    return GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
                })
                .collect(Collectors.toList());
    }

}