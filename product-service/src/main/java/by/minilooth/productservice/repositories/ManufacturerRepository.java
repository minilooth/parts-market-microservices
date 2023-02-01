package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.ManufacturerStatus;
import by.minilooth.productservice.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findAllByStatusNotOrderByShortNameDesc(ManufacturerStatus status);

}
