package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.Model;
import by.minilooth.vehicleservice.services.api.NestedCreateService;
import by.minilooth.vehicleservice.services.api.NestedReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface ModelService extends UpdateDeleteService<Model, Long>, NestedCreateService<Model>, NestedReadService<Model, Long> {

}
