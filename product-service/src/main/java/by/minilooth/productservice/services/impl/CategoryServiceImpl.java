package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.CategoryStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Category;
import by.minilooth.productservice.repositories.CategoryRepository;
import by.minilooth.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired private CategoryRepository categoryRepository;

    @Override
    public Category create(Category entity) {
        Category created = new Category();

        created.setName(entity.getName());
        created.setStatus(CategoryStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Category update(Long id, Category entity) throws ObjectNotFoundException, ImpossibleActionException {
        Category updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find category with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed category with id %s", id));
        }

        updated.setName(entity.getName());

        return save(updated);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAllByStatusNotOrderByNameDesc(CategoryStatus.REMOVED);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category removeById(Long id) throws ObjectNotFoundException {
        Category updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find category with id %s", id)));

        updated.setStatus(CategoryStatus.REMOVED);

        return save(updated);
    }

    @Override
    public Category activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Category updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find category with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed category with id %s", id));
        }

        updated.setStatus(CategoryStatus.ACTIVE);

        return save(updated);
    }

    @Override
    public Category deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Category updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find category with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed category with id %s", id));
        }

        updated.setStatus(CategoryStatus.INACTIVE);

        return save(updated);
    }

    private Category save(Category category) {
        return categoryRepository.save(category);
    }

}
