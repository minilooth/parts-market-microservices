package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.CityStatus;
import by.minilooth.productservice.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByStatusNotOrderByNameDesc(CityStatus status);

}
