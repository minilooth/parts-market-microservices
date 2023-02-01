package by.minilooth.productservice.dtos;

import by.minilooth.productservice.dtos.keys.CrossKeyDto;
import by.minilooth.productservice.models.api.BaseEntity;
import lombok.Data;

@Data
public class CrossDto implements BaseEntity {

    private CrossKeyDto id;
    private ProductDto product;
    private ManufacturerDto manufacturer;
    private String article;

}
