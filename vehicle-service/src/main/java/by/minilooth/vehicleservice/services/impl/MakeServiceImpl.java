package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.models.Make;
import by.minilooth.vehicleservice.common.enums.MakeStatus;
import by.minilooth.vehicleservice.repositories.MakeRepository;
import by.minilooth.vehicleservice.services.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MakeServiceImpl implements MakeService {

    @Autowired private MakeRepository makeRepository;

    @Override
    public Make create(Make entity) {
        Make make = new Make();

        make.setName(entity.getName());
        make.setStatus(MakeStatus.ACTIVE);

        return save(make);
    }

    @Override
    public Make update(Long id, Make entity) throws ObjectNotFoundException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Make with id %s", id)));

        make.setName(entity.getName());

        return save(make);
    }

    @Override
    public List<Make> findAllActive() {
        return makeRepository.findAllByStatusOrderByNameDesc(MakeStatus.ACTIVE);
    }

    @Override
    public List<Make> findAll() {
        return makeRepository.findAll();
    }

    @Override
    public Optional<Make> findById(Long id) {
        return makeRepository.findById(id);
    }

    @Override
    public Make removeById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, MakeStatus.REMOVED);
    }

    @Override
    public Make activateById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, MakeStatus.ACTIVE);
    }

    private Make updateStatus(Long id, MakeStatus status) throws ObjectNotFoundException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Make with id %s", id)));

        make.setStatus(status);

        return save(make);
    }

    private Make save(Make make) {
        return makeRepository.save(make);
    }

}
