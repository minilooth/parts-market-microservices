package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.ManufacturerDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Manufacturer;
import org.springframework.stereotype.Component;

@Component
public class ManufacturerMapper extends AbstractMapper<Manufacturer, ManufacturerDto> {

    protected ManufacturerMapper() {
        super(Manufacturer.class, ManufacturerDto.class);
    }

}
