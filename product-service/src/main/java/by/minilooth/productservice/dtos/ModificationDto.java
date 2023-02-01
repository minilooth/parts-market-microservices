package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.ModificationStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModificationDto extends AbstractDto {

    private String name;
    private ModificationStatus status;

}
