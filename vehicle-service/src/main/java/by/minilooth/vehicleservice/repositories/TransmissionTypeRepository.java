package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.TransmissionType;
import by.minilooth.vehicleservice.common.enums.TransmissionTypeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Long> {

    List<TransmissionType> findAllByStatusNotOrderByName(TransmissionTypeStatus status);

}
