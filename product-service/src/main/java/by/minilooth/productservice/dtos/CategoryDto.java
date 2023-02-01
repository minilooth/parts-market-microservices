package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.CategoryStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDto extends AbstractDto {

    private String name;
    private CategoryStatus status;

}
