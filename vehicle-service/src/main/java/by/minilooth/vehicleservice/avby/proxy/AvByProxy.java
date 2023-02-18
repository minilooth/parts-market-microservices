package by.minilooth.vehicleservice.avby.proxy;

import by.minilooth.vehicleservice.avby.beans.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AV-BY", url = "https://api.av.by/offer-types/cars/filters/main")
public interface AvByProxy {

    @PostMapping("/update")
    AvByResponse execute(@RequestBody AvByRequest request);

}
