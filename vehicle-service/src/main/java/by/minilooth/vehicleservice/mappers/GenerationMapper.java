package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.GenerationDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.beans.Model;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GenerationMapper extends AbstractMapper<Generation, GenerationDto> {

    protected GenerationMapper() {
        super(Generation.class, GenerationDto.class);
    }

    @PostConstruct
    private void setup() {
        mapper.createTypeMap(Generation.class, GenerationDto.class)
                .addMappings(ctx -> ctx.skip(GenerationDto::setModelId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(GenerationDto.class, Generation.class)
                .addMappings(ctx -> ctx.skip(Generation::setModel))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Generation source, GenerationDto destination) {
        destination.setModelId(source.getModel().getId());
    }

    @Override
    public void mapSpecificFields(GenerationDto source, Generation destination) {
        Model model = new Model();
        model.setId(source.getModelId());
        destination.setModel(model);
    }

}
