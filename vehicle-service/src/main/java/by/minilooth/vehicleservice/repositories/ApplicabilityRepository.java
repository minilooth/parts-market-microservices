package by.minilooth.vehicleservice.repositories;

import by.minilooth.vehicleservice.beans.Applicability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicabilityRepository extends JpaRepository<Applicability, Long> {

    List<Applicability> findAllByIdProductId(Long productId);

}
