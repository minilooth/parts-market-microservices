package by.minilooth.vehicleservice.beans;

import by.minilooth.vehicleservice.beans.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.EngineTypeStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "engine_type")
public class EngineType extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EngineTypeStatus status;

    @OneToMany(mappedBy = "engineType")
    @ToString.Exclude
    private Set<Vehicle> vehicles = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(EngineTypeStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EngineType that = (EngineType) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
