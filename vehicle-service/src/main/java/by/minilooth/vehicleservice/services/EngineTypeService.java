package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.EngineType;
import by.minilooth.vehicleservice.services.api.CreateService;
import by.minilooth.vehicleservice.services.api.ReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface EngineTypeService extends UpdateDeleteService<EngineType, Long>, CreateService<EngineType>, ReadService<EngineType> {
}
