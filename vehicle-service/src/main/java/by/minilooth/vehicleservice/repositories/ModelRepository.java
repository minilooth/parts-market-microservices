package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.Model;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findAllByMakeIdAndStatusNotOrderByName(Long makeId, ModelStatus status);

    Boolean existsByIdAndMakeId(Long modelId, Long makeId);

    @Query("SELECT COUNT(m) > 0 FROM Model m WHERE m.id = :modelId AND m.make.id = :makeId")
    Boolean isModelLinkedWithMake(Long modelId, Long makeId);

}
