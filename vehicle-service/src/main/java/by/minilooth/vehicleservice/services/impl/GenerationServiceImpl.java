package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.models.Generation;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.repositories.GenerationRepository;
import by.minilooth.vehicleservice.services.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenerationServiceImpl implements GenerationService {

    @Autowired private GenerationRepository generationRepository;

    @Override
    public Generation create(Generation entity) {
        Generation generation = new Generation();

        generation.setName(entity.getName());
        generation.setStatus(GenerationStatus.ACTIVE);
        generation.setModel(entity.getModel());

        return save(generation);
    }

    @Override
    public Generation update(Long id, Generation entity) throws ObjectNotFoundException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Generation with id %s", id)));

        generation.setName(entity.getName());
        generation.setModel(entity.getModel());

        return save(generation);
    }

    @Override
    public List<Generation> findAllActive(Long modelId) {
        return generationRepository.findAllByModelIdAndStatusOrderByNameDesc(modelId, GenerationStatus.ACTIVE);
    }

    @Override
    public List<Generation> findAll(Long modelId) {
        return generationRepository.findAllByModelIdOrderByNameDesc(modelId);
    }

    @Override
    public Optional<Generation> findById(Long id) {
        return generationRepository.findById(id);
    }

    @Override
    public Generation removeById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, GenerationStatus.REMOVED);
    }

    @Override
    public Generation activateById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, GenerationStatus.ACTIVE);
    }

    private Generation updateStatus(Long id, GenerationStatus status) throws ObjectNotFoundException {
        Generation generation = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Generation with id %s", id)));

        generation.setStatus(status);

        return save(generation);
    }

    private Generation save(Generation generation) {
        return generationRepository.save(generation);
    }

}
