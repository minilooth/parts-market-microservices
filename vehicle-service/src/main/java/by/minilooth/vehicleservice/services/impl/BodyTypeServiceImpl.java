package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.beans.BodyType;
import by.minilooth.vehicleservice.common.enums.BodyTypeStatus;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.repositories.BodyTypeRepository;
import by.minilooth.vehicleservice.services.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BodyTypeServiceImpl implements BodyTypeService {

    @Autowired private BodyTypeRepository bodyTypeRepository;

    @Override
    public BodyType create(BodyType request) {
        BodyType bodyType = new BodyType();

        bodyType.setName(request.getName().trim());
        bodyType.setStatus(BodyTypeStatus.ACTIVE);

        return save(bodyType);
    }

    @Override
    public BodyType update(Long id, BodyType request) throws ObjectNotFoundException, ImpossibleActionException {
        BodyType stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find body type with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed body type with id %s", id));
        }

        stored.setName(request.getName().trim());

        return save(stored);
    }

    @Override
    public Optional<BodyType> findById(Long id) {
        return bodyTypeRepository.findById(id);
    }

    @Override
    public BodyType deleteById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        BodyType bodyType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find body type with id %s", id)));

        if (!bodyType.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting body type allowed only in REMOVED status");
        }

        bodyTypeRepository.delete(bodyType);
        return bodyType;
    }

    @Override
    public BodyType removeById(Long id) throws ObjectNotFoundException {
        BodyType bodyType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find body type with id %s", id)));

        bodyType.setStatus(BodyTypeStatus.REMOVED);

        return save(bodyType);
    }

    @Override
    public BodyType activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        BodyType bodyType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find body type with id %s", id)));

        if (bodyType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed body type with id %s", id));
        }

        bodyType.setStatus(BodyTypeStatus.ACTIVE);

        return save(bodyType);
    }

    @Override
    public BodyType deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        BodyType bodyType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find body type with id %s", id)));

        if (bodyType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed body type with id %s", id));
        }

        bodyType.setStatus(BodyTypeStatus.INACTIVE);

        return save(bodyType);
    }

    @Override
    public List<BodyType> findAll() {
        return bodyTypeRepository.findAllByStatusNotOrderByName(BodyTypeStatus.REMOVED);
    }

    private BodyType save(BodyType bodyType) {
        return bodyTypeRepository.save(bodyType);
    }

}
