package by.minilooth.apigateway.services;

import by.minilooth.apigateway.models.Product;
import by.minilooth.apigateway.models.products.Characteristic;
import by.minilooth.apigateway.models.products.Cross;
import by.minilooth.apigateway.models.products.ProductDetails;
import by.minilooth.apigateway.models.vehicle.Vehicle;
import by.minilooth.apigateway.proxy.ProductProxy;
import by.minilooth.apigateway.proxy.VehicleProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired private ProductProxy productProxy;
    @Autowired private VehicleProxy vehicleProxy;

    public Product getById(Long id) {
        Mono<Optional<ProductDetails>> productDetailsMono = productProxy.getById(id)
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        Mono<Optional<List<Vehicle>>> vehiclesMono = vehicleProxy.getVehiclesByProductId(id)
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        Mono<Optional<List<Characteristic>>> characteristicsMono = productProxy.getCharacteristicsByProductId(id)
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

        Mono<Optional<List<Cross>>> crossesMono = productProxy.getCrossesByProductId(id)
                .map(Optional::of)
                .onErrorReturn(Optional.empty());

//        Mono<Tuple4<ProductDetails, List<Vehicle>, List<Characteristic>, List<Cross>>> tupleMono = Tuple4.when(productDetailsMono, vehiclesMono, characteristicsMono, characteristicsMono);

        return null;
    }


}
