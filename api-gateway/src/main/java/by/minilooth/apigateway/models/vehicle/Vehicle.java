package by.minilooth.apigateway.models.vehicle;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends AbstractEntity {

    private Make make;
    private Model model;
    private Generation generation;
    private Engine engine;
    private Transmission transmission;
    private Body body;

}
