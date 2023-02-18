package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.products.keys.CrossKey;
import lombok.Data;

@Data
public class Cross {

    private CrossKey key;
    private Manufacturer manufacturer;
    private String article;

}
