package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.SubcategoryStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Subcategory;
import by.minilooth.productservice.repositories.SubcategoryRepository;
import by.minilooth.productservice.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired private SubcategoryRepository subcategoryRepository;

    @Override
    public Subcategory create(Subcategory entity) {
        Subcategory created = new Subcategory();

        created.setName(entity.getName());
        created.setCategory(entity.getCategory());
        created.setStatus(SubcategoryStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Subcategory update(Long id, Subcategory entity) throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find subcategory with id %s", id)));

        if (subcategory.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed subcategory with id %s", id));
        }

        subcategory.setName(entity.getName());
        subcategory.setCategory(entity.getCategory());

        return save(subcategory);
    }

    @Override
    public List<Subcategory> findAll(Long id) {
        return subcategoryRepository.findAllByCategoryIdAndStatusNotOrderByNameDesc(id, SubcategoryStatus.REMOVED);
    }

    @Override
    public Optional<Subcategory> findById(Long id) {
        return subcategoryRepository.findById(id);
    }

    @Override
    public Subcategory removeById(Long id) throws ObjectNotFoundException {
        Subcategory subcategory = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find subcategory with id %s", id)));

        subcategory.setStatus(SubcategoryStatus.REMOVED);

        return save(subcategory);
    }

    @Override
    public Subcategory activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find subcategory with id %s", id)));

        if (subcategory.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed subcategory with id %s", id));
        }

        subcategory.setStatus(SubcategoryStatus.ACTIVE);

        return save(subcategory);
    }

    @Override
    public Subcategory deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find subcategory with id %s", id)));

        if (subcategory.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed subcategory with id %s", id));
        }

        subcategory.setStatus(SubcategoryStatus.INACTIVE);

        return save(subcategory);
    }

    private Subcategory save(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

}
