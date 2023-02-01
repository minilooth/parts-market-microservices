package by.minilooth.productservice.models;

import by.minilooth.productservice.models.api.BaseEntity;
import by.minilooth.productservice.models.keys.CrossKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tcross")
@RequiredArgsConstructor
public class Cross implements BaseEntity {

    @EmbeddedId
    private CrossKey id = new CrossKey();

    @ToString.Exclude
    @MapsId("productId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @MapsId("manufacturerId")
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "article", nullable = false)
    private String article;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cross cross = (Cross) o;
        return id != null && Objects.equals(id, cross.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
