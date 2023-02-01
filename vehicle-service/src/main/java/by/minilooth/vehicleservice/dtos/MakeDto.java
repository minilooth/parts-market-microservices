package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.MakeStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MakeDto extends AbstractDto {

    private String name;
    private MakeStatus status;

}
