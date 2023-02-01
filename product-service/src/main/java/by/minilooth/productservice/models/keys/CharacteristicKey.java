package by.minilooth.productservice.models.keys;

import by.minilooth.productservice.models.api.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CharacteristicKey implements Serializable, BaseEntity {

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "modification_id", nullable = false)
    private Long modificationId;

}
