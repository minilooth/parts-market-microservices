package by.minilooth.productservice.services.api;

import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;

import java.util.Optional;

public interface CudService<T> {

    T create(T entity);
    T update(Long id, T entity) throws ObjectNotFoundException, ImpossibleActionException;
    Optional<T> findById(Long id);
    T removeById(Long id) throws ObjectNotFoundException;
    T activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException;
    T deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException;

}
