package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.CityStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CityDto extends AbstractDto {

    private String name;
    private CityStatus status;

}
