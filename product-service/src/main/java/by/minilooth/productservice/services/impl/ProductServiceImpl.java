package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.ProductStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Product;
import by.minilooth.productservice.models.filters.ProductFilter;
import by.minilooth.productservice.repositories.ProductRepository;
import by.minilooth.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository productRepository;

    @Override
    public Product create(Product entity) {
        Product created = new Product();

        created.setCategory(entity.getCategory());
        created.setSubcategory(entity.getSubcategory());
        created.setGroup(entity.getGroup());
        created.setManufacturer(entity.getManufacturer());
        created.setName(entity.getName());
        created.setArticle(entity.getArticle());
        created.setDescription(entity.getDescription());
        created.setStatus(ProductStatus.ACTIVE);
        created.setCharacteristics(entity.getCharacteristics());
        created.setCrosses(entity.getCrosses());

        return save(created);
    }

    @Override
    public Product update(Long id, Product entity) throws ObjectNotFoundException, ImpossibleActionException {
        Product updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find product with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed product with id %s", id));
        }

        updated.setCategory(entity.getCategory());
        updated.setSubcategory(entity.getSubcategory());
        updated.setGroup(entity.getGroup());
        updated.setManufacturer(entity.getManufacturer());
        updated.setName(entity.getName());
        updated.setArticle(entity.getArticle());
        updated.setDescription(entity.getDescription());
        updated.setCharacteristics(entity.getCharacteristics());
        updated.setCrosses(entity.getCrosses());

        return save(updated);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product removeById(Long id) throws ObjectNotFoundException {
        Product product = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find product with id %s", id)));

        product.setStatus(ProductStatus.REMOVED);

        return save(product);
    }

    @Override
    public Product activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Product product = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find product with id %s", id)));

        if (product.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed product with id %s", id));
        }

        product.setStatus(ProductStatus.ACTIVE);

        return save(product);
    }

    @Override
    public Product deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Product product = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find product with id %s", id)));

        if (product.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed product with id %s", id));
        }

        product.setStatus(ProductStatus.INACTIVE);

        return save(product);
    }

    @Override
    public Page<Product> findAll(ProductFilter filter, Pageable pageable) {
        return productRepository.findAllByStatusNot(ProductStatus.REMOVED, Specification.where(null), pageable);
    }

    private Product save(Product product) {
        return productRepository.save(product);
    }

}
