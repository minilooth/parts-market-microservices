package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.CityStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.City;
import by.minilooth.productservice.repositories.CityRepository;
import by.minilooth.productservice.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    @Autowired private CityRepository cityRepository;

    @Override
    public City create(City city) {
        City created = new City();

        created.setName(city.getName());
        created.setStatus(CityStatus.ACTIVE);

        return save(created);
    }

    @Override
    public City update(Long id, City city) throws ObjectNotFoundException, ImpossibleActionException {
        City updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find city with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed city with id %s", id));
        }

        updated.setName(city.getName());

        return save(updated);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAllByStatusNotOrderByNameDesc(CityStatus.REMOVED);
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City removeById(Long id) throws ObjectNotFoundException {
        City city = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find city with id %s", id)));

        city.setStatus(CityStatus.REMOVED);

        return save(city);
    }

    @Override
    public City activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        City city = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find city with id %s", id)));

        if (city.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed city with id %s", id));
        }

        city.setStatus(CityStatus.ACTIVE);

        return save(city);
    }

    @Override
    public City deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        City city = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find city with id %s", id)));

        if (city.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed city with id %s", id));
        }

        city.setStatus(CityStatus.INACTIVE);

        return save(city);
    }

    private City save(City city) {
        return cityRepository.save(city);
    }

}
