package by.minilooth.apigateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


@Component
public class LoggingFilter implements GlobalFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    private final static String UNKNOWN_ROUTE = "Unknown";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Set<URI> sources = exchange.getAttributeOrDefault(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
        URI destination = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

        String originalUri = sources.stream().findFirst().map(URI::toString).orElse(UNKNOWN_ROUTE);
        String routeUri = Objects.nonNull(destination) ? destination.toString() : UNKNOWN_ROUTE;

        LOGGER.info(String.format("Route: %s -> %s", originalUri, routeUri));

        return chain.filter(exchange);
    }

}