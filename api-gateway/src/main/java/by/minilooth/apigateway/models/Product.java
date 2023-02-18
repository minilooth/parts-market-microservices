package by.minilooth.apigateway.models;

import by.minilooth.apigateway.models.api.AbstractEntity;
import by.minilooth.apigateway.models.products.*;
import by.minilooth.apigateway.models.vehicle.Vehicle;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity {

    private Category category;
    private Subcategory subcategory;
    private Group group;
    private Manufacturer manufacturer;
    private String name;
    private String article;
    private String description;
    private String status;
    private List<Characteristic> characteristics;
    private List<Cross> crosses;
    private List<Vehicle> vehicles;

    public static Product create(ProductDetails productDetails, List<Vehicle> vehicles, List<Characteristic> characteristics, List<Cross> crosses) {
        Product product = new Product();
        product.setId(productDetails.getId());
        product.setCategory(productDetails.getCategory());
        product.setSubcategory(productDetails.getSubcategory());
        product.setGroup(productDetails.getGroup());
        product.setManufacturer(productDetails.getManufacturer());
        product.setName(productDetails.getName());
        product.setArticle(productDetails.getArticle());
        product.setDescription(productDetails.getDescription());
        product.setStatus(productDetails.getStatus());
        product.setCharacteristics(characteristics);
        product.setCrosses(crosses);
        product.setVehicles(vehicles);
        product.setCreatedAt(productDetails.getCreatedAt());
        product.setUpdatedAt(productDetails.getUpdatedAt());
        return product;
    }

}
