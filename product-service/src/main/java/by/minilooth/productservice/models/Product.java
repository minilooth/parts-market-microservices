package by.minilooth.productservice.models;

import by.minilooth.productservice.common.enums.ProductStatus;
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
@Table(name = "product")
@RequiredArgsConstructor
public class Product extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "article", nullable = false)
    private String article;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Characteristic> characteristics = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Cross> crosses = new HashSet<>();

    @Override
    public Boolean isEntityRemoved() {
        return this.status.equals(ProductStatus.REMOVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
