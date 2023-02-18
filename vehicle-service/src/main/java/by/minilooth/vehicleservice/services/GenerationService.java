package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.services.api.NestedCreateService;
import by.minilooth.vehicleservice.services.api.NestedReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface GenerationService extends UpdateDeleteService<Generation, Long>, NestedCreateService<Generation>, NestedReadService<Generation, Long> {

}
