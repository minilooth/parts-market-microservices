package by.minilooth.apigateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(DocumentationConfiguration.class);

    @Autowired private RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (Objects.isNull(definitions)) {
            return Collections.emptyList();
        }
        return definitions.stream()
                .peek(definition -> LOGGER.info(String.format("Found route with id=%s and uri=%s",
                        definition.getId(), definition.getUri().toString())))
                .filter(definition -> definition.getId().matches(".*-service"))
                .map(definition -> {
                    String name = definition.getId().replaceAll("-service", "");
                    return GroupedOpenApi.builder()
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                })
                .collect(Collectors.toList());
    }

}