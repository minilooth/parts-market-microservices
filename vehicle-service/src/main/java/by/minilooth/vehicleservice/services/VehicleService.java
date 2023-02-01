package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.models.Vehicle;
import by.minilooth.vehicleservice.services.api.CommonService;

import java.util.List;

public interface VehicleService extends CommonService<Vehicle, Long> {

    List<Vehicle> findAll();
    List<Vehicle> findAllActive();

}
