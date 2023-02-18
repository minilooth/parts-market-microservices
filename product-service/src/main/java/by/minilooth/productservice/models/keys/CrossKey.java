package by.minilooth.productservice.models.keys;

import by.minilooth.productservice.models.api.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CrossKey implements Serializable, BaseEntity {

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "manufacturer_id", nullable = false)
    private Long manufacturerId;

}
