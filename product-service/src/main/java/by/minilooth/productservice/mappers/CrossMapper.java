package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.CrossDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Cross;
import org.springframework.stereotype.Component;

@Component
public class CrossMapper extends AbstractMapper<Cross, CrossDto> {

    protected CrossMapper() {
        super(Cross.class, CrossDto.class);
    }

}
