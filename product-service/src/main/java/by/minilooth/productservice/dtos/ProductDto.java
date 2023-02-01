package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.ProductStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDto extends AbstractDto {

    private CategoryDto category;
    private SubcategoryDto subcategory;
    private GroupDto group;
    private ManufacturerDto manufacturer;
    private String name;
    private String article;
    private String description;
    private ProductStatus status;
    private Set<CharacteristicDto> characteristics;
    private Set<CrossDto> crosses;

}
