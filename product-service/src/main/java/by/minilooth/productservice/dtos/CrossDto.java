package by.minilooth.productservice.dtos;

import by.minilooth.productservice.dtos.api.BaseDto;
import by.minilooth.productservice.dtos.keys.CrossKeyDto;
import lombok.Data;

@Data
public class CrossDto implements BaseDto {

    private CrossKeyDto id;
    private ManufacturerDto manufacturer;
    private String article;

}
