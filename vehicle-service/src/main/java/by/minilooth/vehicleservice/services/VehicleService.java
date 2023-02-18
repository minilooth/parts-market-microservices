package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.Vehicle;
import by.minilooth.vehicleservice.services.api.CreateService;
import by.minilooth.vehicleservice.services.api.ReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

import java.util.List;

public interface VehicleService extends UpdateDeleteService<Vehicle, Long>, CreateService<Vehicle>, ReadService<Vehicle> {

    List<Vehicle> findAllByProductId(Long productId);

}
