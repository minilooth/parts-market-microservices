package by.minilooth.productservice.repositories;

import by.minilooth.productservice.models.Cross;
import by.minilooth.productservice.models.keys.CrossKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrossRepository extends JpaRepository<Cross, CrossKey> {

    List<Cross> findAllByProductId(Long productId);

}
