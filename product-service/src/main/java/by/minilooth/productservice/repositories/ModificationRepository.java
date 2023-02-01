package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.ModificationStatus;
import by.minilooth.productservice.models.Modification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModificationRepository extends JpaRepository<Modification, Long> {

    List<Modification> findAllByStatusNotOrderByNameDesc(ModificationStatus status);

}
