package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.BodyType;
import by.minilooth.vehicleservice.common.enums.BodyTypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

    List<BodyType> findAllByStatusNotOrderByName(BodyTypeStatus status);

}
