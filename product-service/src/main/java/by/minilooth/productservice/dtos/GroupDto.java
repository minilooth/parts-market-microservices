package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.GroupStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupDto extends AbstractDto {

    private String name;
    private Long categoryId;
    private Long subcategoryId;
    private GroupStatus status;

}
