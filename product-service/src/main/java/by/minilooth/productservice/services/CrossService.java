package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Cross;

import java.util.List;

public interface CrossService {

    List<Cross> findAllByProductId(Long productId);

}
