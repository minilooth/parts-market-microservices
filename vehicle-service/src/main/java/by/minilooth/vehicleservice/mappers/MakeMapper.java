package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.MakeDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.models.Make;
import org.springframework.stereotype.Component;

@Component
public class MakeMapper extends AbstractMapper<Make, MakeDto> {

    protected MakeMapper() {
        super(Make.class, MakeDto.class);
    }

}
