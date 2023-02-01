package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.models.Generation;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {

    List<Generation> findAllByModelIdOrderByNameDesc(Long modelId);
    List<Generation> findAllByModelIdAndStatusOrderByNameDesc(Long modelId, GenerationStatus status);

}
