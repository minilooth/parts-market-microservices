package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.SubcategoryStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubcategoryDto extends AbstractDto {

    private String name;
    private SubcategoryStatus status;
    private CategoryDto category;

}
