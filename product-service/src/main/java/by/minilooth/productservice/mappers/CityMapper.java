package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.CityDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper extends AbstractMapper<City, CityDto> {

    protected CityMapper() {
        super(City.class, CityDto.class);
    }

}
