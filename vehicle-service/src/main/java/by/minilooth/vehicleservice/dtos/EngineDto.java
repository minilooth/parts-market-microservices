package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.EngineType;
import by.minilooth.vehicleservice.dtos.api.BaseDto;
import lombok.Data;

@Data
public class EngineDto implements BaseDto {

    private EngineType type;
    private Float volume;
    private Float powerHp;
    private Float powerKw;

}
