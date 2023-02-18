package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Modification extends AbstractEntity {

    private String name;
    private String status;

}
