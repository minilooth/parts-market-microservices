package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.SubcategoryStatus;
import by.minilooth.productservice.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findAllByCategoryIdAndStatusNotOrderByNameDesc(Long categoryId, SubcategoryStatus status);

}
