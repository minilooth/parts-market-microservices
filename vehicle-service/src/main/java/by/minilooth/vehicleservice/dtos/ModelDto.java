package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.ModelStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModelDto extends AbstractDto {

    private String name;
    private Long makeId;
    private ModelStatus status;

}
