package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.GenerationDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.models.Generation;
import org.springframework.stereotype.Component;

@Component
public class GenerationMapper extends AbstractMapper<Generation, GenerationDto> {

    protected GenerationMapper() {
        super(Generation.class, GenerationDto.class);
    }

}
