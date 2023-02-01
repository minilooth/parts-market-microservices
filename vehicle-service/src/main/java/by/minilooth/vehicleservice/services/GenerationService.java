package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.models.Generation;
import by.minilooth.vehicleservice.services.api.CommonService;

import java.util.List;

public interface GenerationService extends CommonService<Generation, Long> {

    List<Generation> findAll(Long makeId);
    List<Generation> findAllActive(Long modelId);

}
