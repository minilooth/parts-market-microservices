package by.minilooth.apigateway.models.products;

import by.minilooth.apigateway.models.api.AbstractEntity;
import by.minilooth.apigateway.models.products.Category;
import by.minilooth.apigateway.models.products.Group;
import by.minilooth.apigateway.models.products.Manufacturer;
import by.minilooth.apigateway.models.products.Subcategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetails extends AbstractEntity {

    private Category category;
    private Subcategory subcategory;
    private Group group;
    private Manufacturer manufacturer;
    private String name;
    private String article;
    private String description;
    private String status;

}
