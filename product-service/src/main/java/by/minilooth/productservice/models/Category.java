package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.CategoryStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "category")
@RequiredArgsConstructor
public class Category extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CategoryStatus status;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private Set<Subcategory> subcategories = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(CategoryStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
