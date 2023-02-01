package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.AddressStatus;
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
@Getter
@Setter
@ToString
@Table(name = "address")
@RequiredArgsConstructor
public class Address extends AbstractEntity {

    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @Column(name = "post_index", nullable = false, length = 10)
    private String postIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AddressStatus status;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ToString.Exclude
    @OneToMany(mappedBy = "address")
    private Set<Manufacturer> manufacturers = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(AddressStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != null && Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
