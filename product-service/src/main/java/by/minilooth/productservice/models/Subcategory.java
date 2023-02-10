package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.SubcategoryStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import javax.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "subcategory")
@RequiredArgsConstructor
public class Subcategory extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubcategoryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;

    @OneToMany(mappedBy = "subcategory")
    @ToString.Exclude
    private Set<Group> groups = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(SubcategoryStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Subcategory subcategory = (Subcategory) o;
        return id != null && Objects.equals(id, subcategory.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
