package by.minilooth.productservice.dtos.keys;

import by.minilooth.productservice.dtos.api.BaseDto;
import lombok.Data;

@Data
public class CharacteristicKeyDto implements BaseDto {

    private Long productId;
    private Long modificationId;

}
