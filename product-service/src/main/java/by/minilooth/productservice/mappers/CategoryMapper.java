package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.CategoryDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends AbstractMapper<Category, CategoryDto> {

    protected CategoryMapper() {
        super(Category.class, CategoryDto.class);
    }

}
