package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenerationDto extends AbstractDto {

    private String name;
    private ModelDto model;
    private GenerationStatus status;
    private Long issuedFrom;
    private Long issuedTo;

}
