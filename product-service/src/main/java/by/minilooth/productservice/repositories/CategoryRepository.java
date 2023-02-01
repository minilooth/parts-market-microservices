package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.CategoryStatus;
import by.minilooth.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByStatusNotOrderByNameDesc(CategoryStatus status);

}
