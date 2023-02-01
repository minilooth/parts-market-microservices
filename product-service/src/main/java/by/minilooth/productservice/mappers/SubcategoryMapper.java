package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.SubcategoryDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Subcategory;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper extends AbstractMapper<Subcategory, SubcategoryDto> {

    protected SubcategoryMapper() {
        super(Subcategory.class, SubcategoryDto.class);
    }

}
