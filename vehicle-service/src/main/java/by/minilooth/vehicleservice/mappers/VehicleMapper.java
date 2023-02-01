package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.VehicleDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.models.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper extends AbstractMapper<Vehicle, VehicleDto> {

    protected VehicleMapper() {
        super(Vehicle.class, VehicleDto.class);
    }

}
