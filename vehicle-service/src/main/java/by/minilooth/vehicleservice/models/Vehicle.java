package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "vehicle")
@RequiredArgsConstructor
public class Vehicle extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private Make make;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    private Generation generation;

    @Column(name = "modification")
    private String modification;

    @Embedded
    private Engine engine;

    @Embedded
    private Transmission transmission;

    @Embedded
    private Body body;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VehicleStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vehicle vehicle = (Vehicle) o;
        return id != null && Objects.equals(id, vehicle.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
