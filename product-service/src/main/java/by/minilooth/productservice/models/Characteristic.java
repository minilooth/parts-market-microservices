package by.minilooth.productservice.models;

import by.minilooth.productservice.models.api.BaseEntity;
import by.minilooth.productservice.models.keys.CharacteristicKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "characteristic")
@RequiredArgsConstructor
public class Characteristic implements BaseEntity {

    @EmbeddedId
    private CharacteristicKey id = new CharacteristicKey();

    @ToString.Exclude
    @MapsId("productId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("modificationId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "modification_id", nullable = false)
    private Modification modification;

    @Column(name = "value", nullable = false)
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Characteristic that = (Characteristic) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
