package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.GroupDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper extends AbstractMapper<Group, GroupDto> {

    protected GroupMapper() {
        super(Group.class, GroupDto.class);
    }

}
