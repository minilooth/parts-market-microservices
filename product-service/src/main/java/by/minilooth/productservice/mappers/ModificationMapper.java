package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.ModificationDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Modification;
import org.springframework.stereotype.Component;

@Component
public class ModificationMapper extends AbstractMapper<Modification, ModificationDto> {

    protected ModificationMapper() {
        super(Modification.class, ModificationDto.class);
    }

}
