package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.models.Make;
import by.minilooth.vehicleservice.services.api.CommonService;

import java.util.List;

public interface MakeService extends CommonService<Make, Long> {

    List<Make> findAll();
    List<Make> findAllActive();


}
