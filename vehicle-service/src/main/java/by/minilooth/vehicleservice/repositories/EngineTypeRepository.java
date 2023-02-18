package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.EngineType;
import by.minilooth.vehicleservice.common.enums.EngineTypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineTypeRepository extends JpaRepository<EngineType, Long> {

    List<EngineType> findAllByStatusNotOrderByName(EngineTypeStatus status);

}
