package by.minilooth.vehicleservice.mappers;

import by.minilooth.vehicleservice.dtos.ModelDto;
import by.minilooth.vehicleservice.mappers.api.AbstractMapper;
import by.minilooth.vehicleservice.beans.Make;
import by.minilooth.vehicleservice.beans.Model;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ModelEntityMapper extends AbstractMapper<Model, ModelDto> {

    protected ModelEntityMapper() {
        super(Model.class, ModelDto.class);
    }

    @PostConstruct
    private void setup() {
        mapper.createTypeMap(Model.class, ModelDto.class)
                .addMappings(ctx -> ctx.skip(ModelDto::setMakeId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(ModelDto.class, Model.class)
                .addMappings(ctx -> ctx.skip(Model::setMake))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Model source, ModelDto destination) {
       destination.setMakeId(source.getMake().getId());
    }

    @Override
    public void mapSpecificFields(ModelDto source, Model destination) {
        Make make = new Make();
        make.setId(source.getMakeId());
        destination.setMake(make);
    }

}
