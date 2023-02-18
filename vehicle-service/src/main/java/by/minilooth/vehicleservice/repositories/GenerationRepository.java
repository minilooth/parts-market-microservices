package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {

    List<Generation> findAllByModelIdAndStatusNotOrderByIssuedFrom(Long modelId, GenerationStatus status);

    Boolean existsByIdAndModelId(Long generationId, Long modelId);

    @Query("SELECT COUNT(g) > 0 FROM Generation g WHERE g.id = :generationId AND g.model.id = :modelId")
    Boolean isGenerationLinkedWithModel(Long generationId, Long modelId);

}
