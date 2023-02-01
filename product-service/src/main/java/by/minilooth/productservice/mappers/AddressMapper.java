package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.AddressDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper extends AbstractMapper<Address, AddressDto> {

    protected AddressMapper() {
        super(Address.class, AddressDto.class);
    }

}
