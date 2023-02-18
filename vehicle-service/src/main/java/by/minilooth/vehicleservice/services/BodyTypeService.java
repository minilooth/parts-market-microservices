package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.BodyType;
import by.minilooth.vehicleservice.services.api.CreateService;
import by.minilooth.vehicleservice.services.api.ReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface BodyTypeService extends UpdateDeleteService<BodyType, Long>, CreateService<BodyType>, ReadService<BodyType> {
}
