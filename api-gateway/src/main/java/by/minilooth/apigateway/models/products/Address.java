package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractEntity {

    private String street;
    private String postIndex;
    private String status;
    private City city;

}
