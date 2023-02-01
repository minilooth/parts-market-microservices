package by.minilooth.vehicleservice.services.api;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;

import java.util.Optional;

public interface CommonService<E, ID> {

    E create(E entity);
    E update(ID id, E entity) throws ObjectNotFoundException;
    Optional<E> findById(ID id);
    E removeById(ID id) throws ObjectNotFoundException;
    E activateById(ID id) throws ObjectNotFoundException;

}
