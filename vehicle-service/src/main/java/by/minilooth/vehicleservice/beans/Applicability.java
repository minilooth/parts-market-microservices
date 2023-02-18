package by.minilooth.vehicleservice.beans;

import by.minilooth.vehicleservice.beans.api.BaseEntity;
import by.minilooth.vehicleservice.beans.keys.ApplicabilityKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "applicability")
@RequiredArgsConstructor
public class Applicability implements BaseEntity {

    @EmbeddedId
    private ApplicabilityKey id = new ApplicabilityKey();

    @ToString.Exclude
    @MapsId("vehicleId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Applicability that = (Applicability) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
