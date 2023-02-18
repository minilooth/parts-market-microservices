package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.BodyTypeStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BodyTypeDto extends AbstractDto {

    private String name;
    private BodyTypeStatus status;

}
