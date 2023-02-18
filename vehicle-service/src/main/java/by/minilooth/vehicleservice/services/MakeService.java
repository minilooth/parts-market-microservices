package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.Make;
import by.minilooth.vehicleservice.services.api.CreateService;
import by.minilooth.vehicleservice.services.api.ReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface MakeService extends UpdateDeleteService<Make, Long>, CreateService<Make>, ReadService<Make> {

}
