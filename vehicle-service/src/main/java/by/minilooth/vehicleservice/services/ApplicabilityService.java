package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.Applicability;

import java.util.List;

public interface ApplicabilityService {

    List<Applicability> findAllByProductId(Long productId);

}
