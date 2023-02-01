package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.ProductStatus;
import by.minilooth.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findAllByStatusNot(ProductStatus productStatus, Specification<Product> specification, Pageable pageable);

}
