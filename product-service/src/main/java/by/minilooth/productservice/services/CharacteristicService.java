package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Characteristic;

import java.util.List;

public interface CharacteristicService {

    List<Characteristic> findAllByProductId(Long productId);

}
