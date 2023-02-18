package by.minilooth.vehicleservice.services;

import by.minilooth.vehicleservice.beans.TransmissionType;
import by.minilooth.vehicleservice.services.api.CreateService;
import by.minilooth.vehicleservice.services.api.ReadService;
import by.minilooth.vehicleservice.services.api.UpdateDeleteService;

public interface TransmissionTypeService extends UpdateDeleteService<TransmissionType, Long>, CreateService<TransmissionType>, ReadService<TransmissionType> {
}
