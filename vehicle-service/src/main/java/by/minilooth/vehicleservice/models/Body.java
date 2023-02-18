package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.BaseEntity;
import by.minilooth.vehicleservice.common.enums.BodyType;
import javax.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Body implements BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type", nullable = false)
    private BodyType type;

}
