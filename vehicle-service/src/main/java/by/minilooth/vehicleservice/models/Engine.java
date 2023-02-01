package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.BaseEntity;
import by.minilooth.vehicleservice.common.enums.EngineType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class Engine implements BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "engine_type", nullable = false)
    private EngineType type;

    @Column(name = "volume", nullable = false)
    private Float volume;

    @Column(name = "power_hp", nullable = false)
    private Float powerHp;

    @Column(name = "power_kw", nullable = false)
    private Float powerKw;

}
