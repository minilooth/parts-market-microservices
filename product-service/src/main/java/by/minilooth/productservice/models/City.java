package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.CityStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "city")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class City extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CityStatus status;

    @ToString.Exclude
    @OneToMany(mappedBy = "city")
    private Set<Address> addresses = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(CityStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        City city = (City) o;
        return id != null && Objects.equals(id, city.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
