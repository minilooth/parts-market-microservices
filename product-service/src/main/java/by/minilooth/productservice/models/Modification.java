package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.ModificationStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "modification")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Modification extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ModificationStatus status;

    @ToString.Exclude
    @OneToMany(mappedBy = "modification")
    private Set<Characteristic> characteristics = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(ModificationStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Modification that = (Modification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
