package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.beans.BodyType;
import by.minilooth.vehicleservice.dtos.BodyTypeDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class BodyTypeMapper extends AbstractMapper<BodyType, BodyTypeDto> {

    public BodyTypeMapper() {
        super(BodyType.class, BodyTypeDto.class);
    }

}
