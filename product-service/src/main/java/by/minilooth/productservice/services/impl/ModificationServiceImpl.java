package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.ModificationStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Modification;
import by.minilooth.productservice.repositories.ModificationRepository;
import by.minilooth.productservice.services.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ModificationServiceImpl implements ModificationService {

    @Autowired private ModificationRepository modificationRepository;

    @Override
    public Modification create(Modification entity) {
        Modification created = new Modification();

        created.setName(entity.getName());
        created.setStatus(ModificationStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Modification update(Long id, Modification entity) throws ObjectNotFoundException, ImpossibleActionException {
        Modification updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find modification with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed modification with id %s", id));
        }

        updated.setName(entity.getName());

        return save(updated);
    }

    @Override
    public Optional<Modification> findById(Long id) {
        return modificationRepository.findById(id);
    }

    @Override
    public Modification removeById(Long id) throws ObjectNotFoundException {
        Modification modification = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find modification with id %s", id)));

        modification.setStatus(ModificationStatus.REMOVED);

        return save(modification);
    }

    @Override
    public Modification activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Modification modification = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find modification with id %s", id)));

        if (modification.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed modification with id %s", id));
        }

        modification.setStatus(ModificationStatus.ACTIVE);

        return save(modification);
    }

    @Override
    public Modification deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Modification modification = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find modification with id %s", id)));

        if (modification.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed modification with id %s", id));
        }

        modification.setStatus(ModificationStatus.INACTIVE);

        return save(modification);
    }

    @Override
    public List<Modification> findAll() {
        return modificationRepository.findAllByStatusNotOrderByNameDesc(ModificationStatus.REMOVED);
    }

    private Modification save(Modification modification) {
        return modificationRepository.save(modification);
    }

}
