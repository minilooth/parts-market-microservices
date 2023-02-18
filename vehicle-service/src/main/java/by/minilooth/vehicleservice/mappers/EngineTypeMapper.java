package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.beans.EngineType;
import by.minilooth.vehicleservice.dtos.EngineTypeDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class EngineTypeMapper extends AbstractMapper<EngineType, EngineTypeDto> {

    protected EngineTypeMapper() {
        super(EngineType.class, EngineTypeDto.class);
    }

}
