package by.minilooth.productservice.repositories;

import by.minilooth.productservice.models.Cross;
import by.minilooth.productservice.models.keys.CrossKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrossRepository extends JpaRepository<Cross, CrossKey> {
}
