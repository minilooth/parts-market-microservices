package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.CharacteristicDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Characteristic;
import org.springframework.stereotype.Component;

@Component
public class CharacteristicMapper extends AbstractMapper<Characteristic, CharacteristicDto> {

    protected CharacteristicMapper() {
        super(Characteristic.class, CharacteristicDto.class);
    }

}
