package by.minilooth.vehicleservice.dtos;

import by.minilooth.vehicleservice.common.enums.TransmissionType;
import by.minilooth.vehicleservice.dtos.api.BaseDto;
import lombok.Data;

@Data
public class TransmissionDto implements BaseDto {

    private TransmissionType type;

}
