package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.BaseEntity;
import by.minilooth.vehicleservice.common.enums.TransmissionType;
import javax.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Transmission implements BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType type;

}
