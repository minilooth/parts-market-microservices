package by.minilooth.vehicleservice.beans;

import by.minilooth.vehicleservice.beans.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.MakeStatus;
import javax.persistence.*;
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
@Table(name = "make")
@RequiredArgsConstructor
public class Make extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected MakeStatus status;

    @OneToMany(mappedBy = "make")
    @ToString.Exclude
    private Set<Model> models = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(MakeStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Make make = (Make) o;
        return id != null && Objects.equals(id, make.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
