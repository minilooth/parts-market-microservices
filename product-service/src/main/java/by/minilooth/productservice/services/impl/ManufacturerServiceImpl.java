package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.ManufacturerStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Manufacturer;
import by.minilooth.productservice.repositories.ManufacturerRepository;
import by.minilooth.productservice.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Manufacturer created = new Manufacturer();

        created.setShortName(manufacturer.getShortName());
        created.setFullName(manufacturer.getFullName());
        created.setDescription(manufacturer.getDescription());
        created.setPhoneNumber(manufacturer.getPhoneNumber());
        created.setWebsite(manufacturer.getWebsite());
        created.setAddress(manufacturer.getAddress());
        created.setStatus(ManufacturerStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Manufacturer update(Long id, Manufacturer manufacturer) throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find manufacturer with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed manufacturer with id %s", id));
        }

        updated.setShortName(manufacturer.getShortName());
        updated.setFullName(manufacturer.getFullName());
        updated.setDescription(manufacturer.getDescription());
        updated.setPhoneNumber(manufacturer.getPhoneNumber());
        updated.setWebsite(manufacturer.getWebsite());
        updated.setAddress(manufacturer.getAddress());

        return save(updated);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAllByStatusNotOrderByShortNameDesc(ManufacturerStatus.REMOVED);
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public Manufacturer removeById(Long id) throws ObjectNotFoundException {
        Manufacturer manufacturer = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find manufacturer with id %s", id)));

        manufacturer.setStatus(ManufacturerStatus.REMOVED);

        return save(manufacturer);
    }

    @Override
    public Manufacturer activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer manufacturer = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find manufacturer with id %s", id)));

        if (manufacturer.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed manufacturer with id %s", id));
        }

        manufacturer.setStatus(ManufacturerStatus.ACTIVE);

        return save(manufacturer);
    }

    @Override
    public Manufacturer deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer manufacturer = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find manufacturer with id %s", id)));

        if (manufacturer.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed manufacturer with id %s", id));
        }

        manufacturer.setStatus(ManufacturerStatus.INACTIVE);

        return save(manufacturer);
    }

    private Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

}
