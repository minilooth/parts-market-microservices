package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleDto extends AbstractDto {

    private MakeDto make;
    private ModelDto model;
    private GenerationDto generation;
    private String modification;
    private EngineDto engine;
    private TransmissionDto transmission;
    private BodyDto body;
    private VehicleStatus status;

}
