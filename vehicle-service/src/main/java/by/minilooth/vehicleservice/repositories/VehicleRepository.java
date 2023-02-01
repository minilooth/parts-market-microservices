package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.models.Vehicle;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByStatus(VehicleStatus status);

}
