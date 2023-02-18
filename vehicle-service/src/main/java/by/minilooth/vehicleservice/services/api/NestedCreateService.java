package by.minilooth.vehicleservice.services.api;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;

public interface NestedCreateService<T> {

    T create(T entity) throws ObjectNotFoundException;

}
