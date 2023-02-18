package by.minilooth.productservice.repositories;

import by.minilooth.productservice.models.Characteristic;
import by.minilooth.productservice.models.keys.CharacteristicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, CharacteristicKey> {

    List<Characteristic> findAllByProductId(Long productId);

}
