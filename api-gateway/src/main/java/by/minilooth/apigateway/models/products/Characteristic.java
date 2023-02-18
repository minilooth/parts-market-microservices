package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.products.keys.CharacteristicKey;
import lombok.Data;

@Data
public class Characteristic {

    private CharacteristicKey key;
    private Modification modification;
    private String value;

}
