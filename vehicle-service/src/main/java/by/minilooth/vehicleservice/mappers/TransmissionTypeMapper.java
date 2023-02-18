package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.beans.TransmissionType;
import by.minilooth.vehicleservice.dtos.TransmissionTypeDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class TransmissionTypeMapper extends AbstractMapper<TransmissionType, TransmissionTypeDto> {

    protected TransmissionTypeMapper() {
        super(TransmissionType.class, TransmissionTypeDto.class);
    }

}
