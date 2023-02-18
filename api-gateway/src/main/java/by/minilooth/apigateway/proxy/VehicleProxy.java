package by.minilooth.apigateway.proxy;

import by.minilooth.apigateway.models.vehicle.Vehicle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;

@FeignClient(name = "VEHICLE-SERVICE")
public interface VehicleProxy {

    @GetMapping("/vehicle/suit/{productId}")
    Mono<List<Vehicle>> getVehiclesByProductId(@PathVariable Long productId);

}
