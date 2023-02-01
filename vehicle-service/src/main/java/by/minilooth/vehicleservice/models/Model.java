package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "model")
@RequiredArgsConstructor
public class Model extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected ModelStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "make_id", nullable = false)
    @ToString.Exclude
    private Make make;

    @OneToMany(mappedBy = "model")
    @ToString.Exclude
    private Set<Generation> generations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Model model = (Model) o;
        return id != null && Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
