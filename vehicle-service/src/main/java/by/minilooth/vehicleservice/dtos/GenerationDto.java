package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenerationDto extends AbstractDto {

    private String name;
    private Long modelId;
    private GenerationStatus status;
    private Integer issuedFrom;
    private Integer issuedTo;

}
