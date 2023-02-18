package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.TransmissionTypeStatus;
import by.minilooth.vehicleservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransmissionTypeDto extends AbstractDto {

    private String name;
    private TransmissionTypeStatus status;

}
