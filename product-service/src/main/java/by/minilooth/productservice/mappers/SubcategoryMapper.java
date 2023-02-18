package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.SubcategoryDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Category;
import by.minilooth.productservice.models.Subcategory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SubcategoryMapper extends AbstractMapper<Subcategory, SubcategoryDto> {

    protected SubcategoryMapper() {
        super(Subcategory.class, SubcategoryDto.class);
    }

    @PostConstruct
    private void setup() {
        mapper.createTypeMap(Subcategory.class, SubcategoryDto.class)
                .addMappings(ctx -> ctx.skip(SubcategoryDto::setCategoryId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(SubcategoryDto.class, Subcategory.class)
                .addMappings(ctx -> ctx.skip(Subcategory::setCategory))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Subcategory source, SubcategoryDto destination) {
        destination.setCategoryId(source.getCategory().getId());
    }

    @Override
    public void mapSpecificFields(SubcategoryDto source, Subcategory destination) {
        Category category = new Category();
        category.setId(source.getCategoryId());
        destination.setCategory(category);
    }

}
