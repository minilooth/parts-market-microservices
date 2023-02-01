package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.models.Model;
import by.minilooth.vehicleservice.services.api.CommonService;

import java.util.List;

public interface ModelService extends CommonService<Model, Long> {

    List<Model> findAll(Long makeId);
    List<Model> findAllActive(Long makeId);

}
