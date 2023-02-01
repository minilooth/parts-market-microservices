package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.models.Model;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
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

    @Autowired private ModelRepository modelRepository;

    @Override
    public Model create(Model entity) {
        Model model = new Model();

        model.setName(entity.getName());
        model.setStatus(ModelStatus.ACTIVE);
        model.setMake(entity.getMake());

        return save(model);
    }

    @Override
    public Model update(Long id, Model entity) throws ObjectNotFoundException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Model with id %s", id)));

        model.setName(entity.getName());
        model.setMake(entity.getMake());

        return save(model);
    }

    @Override
    public List<Model> findAllActive(Long makeId) {
        return modelRepository.findAllByMakeIdAndStatusOrderByNameDesc(makeId, ModelStatus.ACTIVE);
    }

    @Override
    public List<Model> findAll(Long makeId) {
        return modelRepository.findAllByMakeIdOrderByNameDesc(makeId);
    }

    @Override
    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }

    @Override
    public Model removeById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, ModelStatus.REMOVED);
    }

    @Override
    public Model activateById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, ModelStatus.ACTIVE);
    }

    private Model updateStatus(Long id, ModelStatus status) throws ObjectNotFoundException {
        Model model = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Model with id %s", id)));

        model.setStatus(status);

        return save(model);
    }

    private Model save(Model model) {
        return modelRepository.save(model);
    }

}
