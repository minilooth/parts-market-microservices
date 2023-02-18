package by.minilooth.apigateway.proxy;

import by.minilooth.apigateway.models.products.ProductDetails;
import by.minilooth.apigateway.models.products.Characteristic;
import by.minilooth.apigateway.models.products.Cross;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductProxy {

    @GetMapping("/product/{productId}")
    Mono<ProductDetails> getById(@PathVariable Long productId);

    @GetMapping("/product/characteristic/{productId}")
    Mono<List<Characteristic>> getCharacteristicsByProductId(@PathVariable Long productId);

    @GetMapping("/product/cross/{productId}")
    Mono<List<Cross>> getCrossesByProductId(@PathVariable Long productId);

}
