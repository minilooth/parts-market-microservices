package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.beans.Make;
import by.minilooth.vehicleservice.common.enums.MakeStatus;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
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

    private final MakeRepository makeRepository;

    @Autowired
    public MakeServiceImpl(MakeRepository makeRepository) {
        this.makeRepository = makeRepository;
    }

    @Override
    public Make create(Make request) {
        Make make = new Make();

        make.setName(request.getName().trim());
        make.setStatus(MakeStatus.ACTIVE);

        return save(make);
    }

    @Override
    public Make update(Long id, Make request) throws VehicleApiException {
        Make stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find make with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed make with id %s", id));
        }

        stored.setName(request.getName().trim());

        return save(stored);
    }

    @Override
    public List<Make> findAll() {
        return makeRepository.findAllByStatusNotOrderByName(MakeStatus.REMOVED);
    }

    @Override
    public Optional<Make> findById(Long id) {
        return makeRepository.findById(id);
    }

    @Override
    public Make deleteById(Long id) throws VehicleApiException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find make with id %s", id)));

        if (!make.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting make allowed only in REMOVED status");
        }

        makeRepository.delete(make);

        return make;
    }

    @Override
    public Make removeById(Long id) throws VehicleApiException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find make with id %s", id)));

        make.setStatus(MakeStatus.REMOVED);

        return save(make);
    }

    @Override
    public Make activateById(Long id) throws VehicleApiException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find make with id %s", id)));

        if (make.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed make with id %s", id));
        }

        make.setStatus(MakeStatus.ACTIVE);

        return save(make);
    }

    @Override
    public Make deactivateById(Long id) throws VehicleApiException {
        Make make = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find make with id %s", id)));

        if (make.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed make with id %s", id));
        }

        make.setStatus(MakeStatus.INACTIVE);

        return save(make);
    }

    private Make save(Make make) {
        return makeRepository.save(make);
    }

}
