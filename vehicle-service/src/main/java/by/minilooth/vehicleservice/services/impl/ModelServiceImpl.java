package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.beans.Model;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.repositories.MakeRepository;
import by.minilooth.vehicleservice.repositories.ModelRepository;
import by.minilooth.vehicleservice.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final MakeRepository makeRepository;

    @Autowired
    public ModelServiceImpl(ModelRepository modelRepository,
                            MakeRepository makeRepository) {
        this.modelRepository = modelRepository;
        this.makeRepository = makeRepository;
    }

    @Override
    public Model create(Model request) throws VehicleApiException {
        Model model = new Model();

        if (!makeRepository.existsById(request.getMake().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find make with id %s",
                    request.getMake().getId()));
        }

        model.setName(request.getName().trim());
        model.setStatus(ModelStatus.ACTIVE);
        model.setMake(makeRepository.getReferenceById(request.getMake().getId()));

        return save(model);
    }

    @Override
    public Model update(Long id, Model request) throws VehicleApiException {
        Model stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find model with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed model with id %s", id));
        }

        if (!makeRepository.existsById(request.getMake().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find make with id %s",
                    request.getMake().getId()));
        }

        stored.setName(request.getName().trim());
        stored.setMake(makeRepository.getReferenceById(request.getMake().getId()));

        return save(stored);
    }

    @Override
    public List<Model> findAll(Long makeId) {
        return modelRepository.findAllByMakeIdAndStatusNotOrderByName(makeId, ModelStatus.REMOVED);
    }

    @Override
    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }

    @Override
    public Model deleteById(Long id) throws VehicleApiException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find model with id %s", id)));

        if (!model.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting model allowed only in REMOVED status");
        }

        modelRepository.delete(model);

        return model;
    }

    @Override
    public Model removeById(Long id) throws VehicleApiException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find model with id %s", id)));

        model.setStatus(ModelStatus.REMOVED);

        return save(model);
    }

    @Override
    public Model activateById(Long id) throws VehicleApiException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find model with id %s", id)));

        if (model.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed model with id %s", id));
        }

        model.setStatus(ModelStatus.ACTIVE);

        return save(model);
    }

    @Override
    public Model deactivateById(Long id) throws VehicleApiException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find model with id %s", id)));

        if (model.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed model with id %s", id));
        }

        model.setStatus(ModelStatus.INACTIVE);

        return save(model);
    }

    private Model save(Model model) {
        return modelRepository.save(model);
    }

}
