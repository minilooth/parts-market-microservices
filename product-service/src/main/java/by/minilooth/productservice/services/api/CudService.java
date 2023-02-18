package by.minilooth.productservice.services.api;

import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;

import java.util.Optional;

public interface CudService<T, ID> {

    T create(T entity);
    T update(ID id, T entity) throws ObjectNotFoundException, ImpossibleActionException;
    Optional<T> findById(ID id);
    T removeById(ID id) throws ObjectNotFoundException;
    T activateById(ID id) throws ObjectNotFoundException, ImpossibleActionException;
    T deactivateById(ID id) throws ObjectNotFoundException, ImpossibleActionException;

}
