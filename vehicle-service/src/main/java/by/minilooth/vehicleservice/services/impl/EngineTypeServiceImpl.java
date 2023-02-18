package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.beans.EngineType;
import by.minilooth.vehicleservice.common.enums.EngineTypeStatus;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.repositories.EngineTypeRepository;
import by.minilooth.vehicleservice.services.EngineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EngineTypeServiceImpl implements EngineTypeService {

    private final EngineTypeRepository engineTypeRepository;

    @Autowired
    public EngineTypeServiceImpl(EngineTypeRepository engineTypeRepository) {
        this.engineTypeRepository = engineTypeRepository;
    }

    @Override
    public EngineType create(EngineType request) {
        EngineType engineType = new EngineType();

        engineType.setName(request.getName().trim());
        engineType.setStatus(EngineTypeStatus.ACTIVE);

        return save(engineType);
    }

    @Override
    public EngineType update(Long id, EngineType request) throws VehicleApiException {
        EngineType stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find engine type with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed engine type with id %s", id));
        }

        stored.setName(request.getName().trim());

        return save(stored);
    }

    @Override
    public Optional<EngineType> findById(Long id) {
        return engineTypeRepository.findById(id);
    }

    @Override
    public EngineType deleteById(Long id) throws VehicleApiException {
        EngineType engineType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find engine type with id %s", id)));

        if (!engineType.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting engine type allowed only in REMOVED status");
        }

        engineTypeRepository.delete(engineType);
        return engineType;
    }

    @Override
    public EngineType removeById(Long id) throws VehicleApiException {
        EngineType engineType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find engine type with id %s", id)));

        engineType.setStatus(EngineTypeStatus.REMOVED);

        return save(engineType);
    }

    @Override
    public EngineType activateById(Long id) throws VehicleApiException {
        EngineType engineType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find engine type with id %s", id)));

        if (engineType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed engine type with id %s", id));
        }

        engineType.setStatus(EngineTypeStatus.ACTIVE);

        return save(engineType);
    }

    @Override
    public EngineType deactivateById(Long id) throws VehicleApiException {
        EngineType engineType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find engine type with id %s", id)));

        if (engineType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed engine type with id %s", id));
        }

        engineType.setStatus(EngineTypeStatus.INACTIVE);

        return save(engineType);
    }

    @Override
    public List<EngineType> findAll() {
        return engineTypeRepository.findAllByStatusNotOrderByName(EngineTypeStatus.REMOVED);
    }

    private EngineType save(EngineType engineType) {
        return engineTypeRepository.save(engineType);
    }

}
