package by.minilooth.productservice.mappers;

import by.minilooth.productservice.dtos.ProductDto;
import by.minilooth.productservice.mappers.api.AbstractMapper;
import by.minilooth.productservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto> {

    protected ProductMapper() {
        super(Product.class, ProductDto.class);
    }

}
