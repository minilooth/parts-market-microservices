package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.GroupStatus;
import by.minilooth.productservice.models.api.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "tgroup")
public class Group extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @ToString.Exclude
    private Subcategory subcategory;

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(GroupStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group that = (Group) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
