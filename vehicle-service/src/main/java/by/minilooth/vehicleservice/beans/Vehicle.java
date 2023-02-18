package by.minilooth.vehicleservice.beans;

import by.minilooth.vehicleservice.beans.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "engine_type_id", nullable = false)
    @ToString.Exclude
    private EngineType engineType;

    @Column(name = "volume", nullable = false)
    private Float volume;

    @Column(name = "power_hp", nullable = false)
    private Float powerHp;

    @Column(name = "power_kw", nullable = false)
    private Float powerKw;

    @ManyToOne
    @JoinColumn(name = "transmission_type_id", nullable = false)
    @ToString.Exclude
    private TransmissionType transmissionType;

    @ManyToOne
    @JoinColumn(name = "body_type_id", nullable = false)
    @ToString.Exclude
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VehicleStatus status;

    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    private Set<Applicability> applicabilities = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(VehicleStatus.REMOVED);
    }

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
