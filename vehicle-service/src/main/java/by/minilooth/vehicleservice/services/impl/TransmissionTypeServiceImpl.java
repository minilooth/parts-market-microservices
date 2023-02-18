package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.beans.TransmissionType;
import by.minilooth.vehicleservice.common.enums.TransmissionTypeStatus;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.repositories.TransmissionTypeRepository;
import by.minilooth.vehicleservice.services.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransmissionTypeServiceImpl implements TransmissionTypeService {

    private final TransmissionTypeRepository transmissionTypeRepository;

    @Autowired
    public TransmissionTypeServiceImpl(TransmissionTypeRepository transmissionTypeRepository) {
        this.transmissionTypeRepository = transmissionTypeRepository;
    }

    @Override
    public TransmissionType create(TransmissionType request) {
        TransmissionType transmissionType = new TransmissionType();

        transmissionType.setName(request.getName().trim());
        transmissionType.setStatus(TransmissionTypeStatus.ACTIVE);

        return save(transmissionType);
    }

    @Override
    public TransmissionType update(Long id, TransmissionType request) throws VehicleApiException {
        TransmissionType stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find transmission type with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed transmission type with id %s", id));
        }

        stored.setName(request.getName().trim());

        return save(stored);
    }

    @Override
    public Optional<TransmissionType> findById(Long id) {
        return transmissionTypeRepository.findById(id);
    }

    @Override
    public TransmissionType deleteById(Long id) throws VehicleApiException {
        TransmissionType transmissionType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find transmission type with id %s", id)));

        if (!transmissionType.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting transmission type allowed only in REMOVED status");
        }

        transmissionTypeRepository.delete(transmissionType);
        return transmissionType;
    }

    @Override
    public TransmissionType removeById(Long id) throws VehicleApiException {
        TransmissionType transmissionType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find transmission type with id %s", id)));

        transmissionType.setStatus(TransmissionTypeStatus.REMOVED);

        return save(transmissionType);
    }

    @Override
    public TransmissionType activateById(Long id) throws VehicleApiException {
        TransmissionType transmissionType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find transmission type with id %s", id)));

        if (transmissionType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed transmission type with id %s", id));
        }

        transmissionType.setStatus(TransmissionTypeStatus.ACTIVE);

        return save(transmissionType);
    }

    @Override
    public TransmissionType deactivateById(Long id) throws VehicleApiException {
        TransmissionType transmissionType = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find transmission type with id %s", id)));

        if (transmissionType.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed transmission type with id %s", id));
        }

        transmissionType.setStatus(TransmissionTypeStatus.INACTIVE);

        return save(transmissionType);
    }

    @Override
    public List<TransmissionType> findAll() {
        return transmissionTypeRepository.findAllByStatusNotOrderByName(TransmissionTypeStatus.REMOVED);
    }

    private TransmissionType save(TransmissionType transmissionType) {
        return transmissionTypeRepository.save(transmissionType);
    }

}
