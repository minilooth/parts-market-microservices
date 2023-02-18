package by.minilooth.vehicleservice.services.api;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;

import java.util.Optional;

public interface UpdateDeleteService<T, ID> {

    T update(ID id, T entity) throws VehicleApiException;
    Optional<T> findById(ID id);
    T deleteById(ID id) throws VehicleApiException;
    T removeById(ID id) throws VehicleApiException;
    T activateById(ID id) throws VehicleApiException;
    T deactivateById(ID id) throws VehicleApiException;

}
