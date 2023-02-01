package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.BaseEntity;
import by.minilooth.vehicleservice.common.enums.TransmissionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Transmission implements BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type", nullable = false)
    private TransmissionType type;

}
