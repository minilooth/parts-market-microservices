package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.ManufacturerStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "manufacturer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Manufacturer extends AbstractEntity {

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "website")
    private String website;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ManufacturerStatus status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(ManufacturerStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Manufacturer that = (Manufacturer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
