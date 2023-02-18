package by.minilooth.apigateway.models.vehicle;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Generation extends AbstractEntity {

    private String name;
    private String status;
    private Long issuedFrom;
    private Long issuedTo;
    private Model model;

}
