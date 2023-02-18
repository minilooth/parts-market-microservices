package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.EngineTypeStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EngineTypeDto extends AbstractDto {

    private String name;
    private EngineTypeStatus status;

}
