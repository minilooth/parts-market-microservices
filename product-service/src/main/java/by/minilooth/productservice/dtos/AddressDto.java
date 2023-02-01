package by.minilooth.productservice.dtos;

import by.minilooth.productservice.common.enums.AddressStatus;
import by.minilooth.productservice.dtos.api.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressDto extends AbstractDto {

    private String street;
    private String postIndex;
    private AddressStatus status;
    private CityDto city;

}
