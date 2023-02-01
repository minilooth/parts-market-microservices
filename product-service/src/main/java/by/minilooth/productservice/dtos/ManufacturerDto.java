package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.ManufacturerStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManufacturerDto extends AbstractDto {

    private String shortName;
    private String fullName;
    private String phoneNumber;
    private String website;
    private String description;
    private ManufacturerStatus status;
    private AddressDto address;

}
