package by.minilooth.vehicleservice.services.api;

import by.minilooth.vehicleservice.exceptions.VehicleApiException;

public interface NestedCreateService<T> {

    T create(T entity) throws VehicleApiException;

}
