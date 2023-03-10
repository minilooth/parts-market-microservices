package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.common.enums.MakeStatus;
import by.minilooth.vehicleservice.beans.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MakeRepository extends JpaRepository<Make, Long> {

    List<Make> findAllByStatusNotOrderByName(MakeStatus status);

}
