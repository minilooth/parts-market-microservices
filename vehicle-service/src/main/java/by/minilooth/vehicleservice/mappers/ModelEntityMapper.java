package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.ModelDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.models.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelEntityMapper extends AbstractMapper<Model, ModelDto> {

    protected ModelEntityMapper() {
        super(Model.class, ModelDto.class);
    }

}
