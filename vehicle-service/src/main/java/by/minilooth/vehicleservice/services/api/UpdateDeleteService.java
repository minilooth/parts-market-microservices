package by.minilooth.vehicleservice.services.api;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;

import java.util.Optional;

public interface UpdateDeleteService<T, ID> {

    T update(ID id, T entity) throws ObjectNotFoundException, ImpossibleActionException;
    Optional<T> findById(ID id);
    T deleteById(ID id) throws ObjectNotFoundException, ImpossibleActionException;
    T removeById(ID id) throws ObjectNotFoundException;
    T activateById(ID id) throws ObjectNotFoundException, ImpossibleActionException;
    T deactivateById(ID id) throws ObjectNotFoundException, ImpossibleActionException;

}
