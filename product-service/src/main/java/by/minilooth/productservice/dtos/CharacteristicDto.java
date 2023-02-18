package by.minilooth.productservice.dtos;

import by.minilooth.productservice.dtos.api.BaseDto;
import by.minilooth.productservice.dtos.keys.CharacteristicKeyDto;
import lombok.Data;

@Data
public class CharacteristicDto implements BaseDto {

    private CharacteristicKeyDto id;
    private ModificationDto modification;
    private String value;

}
