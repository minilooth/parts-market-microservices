package by.minilooth.vehicleservice.beans.keys;

import by.minilooth.vehicleservice.beans.api.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ApplicabilityKey implements Serializable, BaseEntity {

    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

}
