package by.minilooth.apigateway.models.vehicle;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Model extends AbstractEntity {

    private String name;
    private String status;
    private Make make;

}
