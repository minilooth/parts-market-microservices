package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.repositories.GenerationRepository;
import by.minilooth.vehicleservice.repositories.ModelRepository;
import by.minilooth.vehicleservice.services.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenerationServiceImpl implements GenerationService {

    private final GenerationRepository generationRepository;
    private final ModelRepository modelRepository;

    @Autowired
    public GenerationServiceImpl(GenerationRepository generationRepository,
                                 ModelRepository modelRepository) {
        this.generationRepository = generationRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public Generation create(Generation request) throws VehicleApiException {
        Generation generation = new Generation();

        if (!modelRepository.existsById(request.getModel().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find model with id %s",
                    request.getModel().getId()));
        }

        generation.setName(request.getName().trim());
        generation.setStatus(GenerationStatus.ACTIVE);
        generation.setModel(modelRepository.getReferenceById(request.getModel().getId()));
        generation.setIssuedFrom(request.getIssuedFrom());
        generation.setIssuedTo(request.getIssuedTo());

        return save(generation);
    }

    @Override
    public Generation update(Long id, Generation request) throws VehicleApiException {
        Generation stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find generation with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed generation with id %s", id));
        }

        if (!modelRepository.existsById(request.getModel().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find model with id %s",
                    request.getModel().getId()));
        }

        stored.setName(request.getName().trim());
        stored.setModel(modelRepository.getReferenceById(request.getModel().getId()));
        stored.setIssuedFrom(request.getIssuedFrom());
        stored.setIssuedTo(request.getIssuedTo());

        return save(stored);
    }

    @Override
    public List<Generation> findAll(Long modelId) {
        return generationRepository.findAllByModelIdAndStatusNotOrderByIssuedFrom(modelId, GenerationStatus.REMOVED);
    }

    @Override
    public Optional<Generation> findById(Long id) {
        return generationRepository.findById(id);
    }

    @Override
    public Generation deleteById(Long id) throws VehicleApiException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find generation with id %s", id)));

        if (!generation.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting generation allowed only in REMOVED status");
        }

        generationRepository.delete(generation);

        return generation;
    }

    @Override
    public Generation removeById(Long id) throws VehicleApiException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find generation with id %s", id)));

        generation.setStatus(GenerationStatus.REMOVED);

        return save(generation);
    }

    @Override
    public Generation activateById(Long id) throws VehicleApiException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find generation with id %s", id)));

        if (generation.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed generation with id %s", id));
        }

        generation.setStatus(GenerationStatus.ACTIVE);

        return save(generation);
    }

    @Override
    public Generation deactivateById(Long id) throws VehicleApiException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find generation with id %s", id)));

        if (generation.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed generation with id %s", id));
        }

        generation.setStatus(GenerationStatus.INACTIVE);

        return save(generation);
    }

    private Generation save(Generation generation) {
        return generationRepository.save(generation);
    }

}
