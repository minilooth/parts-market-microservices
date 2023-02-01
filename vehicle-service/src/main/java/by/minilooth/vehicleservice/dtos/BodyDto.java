package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.BodyType;
import by.minilooth.vehicleservice.dtos.api.BaseDto;
import lombok.Data;

@Data
public class BodyDto implements BaseDto {

    private BodyType type;

}
