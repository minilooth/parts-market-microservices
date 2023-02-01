package by.minilooth.vehicleservice.models;

import by.minilooth.vehicleservice.models.api.AbstractEntity;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "generation")
public class Generation extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GenerationStatus status;

    @Column(name = "issued_from", nullable = false)
    private Long issuedFrom;

    @Column(name = "issued_to")
    private Long issuedTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    @ToString.Exclude
    private Model model;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Generation that = (Generation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
