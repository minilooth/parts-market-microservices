package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.GroupDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Group;
import by.minilooth.productservice.models.Subcategory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GroupMapper extends AbstractMapper<Group, GroupDto> {

    protected GroupMapper() {
        super(Group.class, GroupDto.class);
    }

    @PostConstruct
    private void setup() {
        mapper.createTypeMap(Group.class, GroupDto.class)
                .addMappings(ctx -> ctx.skip(GroupDto::setSubcategoryId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(GroupDto.class, Group.class)
                .addMappings(ctx -> ctx.skip(Group::setSubcategory))
                .setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Group source, GroupDto destination) {
        destination.setSubcategoryId(source.getSubcategory().getId());
        destination.setCategoryId(source.getSubcategory().getCategory().getId());
    }

    @Override
    public void mapSpecificFields(GroupDto source, Group destination) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(source.getSubcategoryId());
        destination.setSubcategory(subcategory);
    }

}
