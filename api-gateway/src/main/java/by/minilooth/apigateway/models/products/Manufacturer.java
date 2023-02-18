package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.api.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Manufacturer extends AbstractEntity {

    private String shortName;
    private String fullName;
    private String phoneNumber;
    private String website;
    private String description;
    private String status;
    private Address address;

}
