package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.beans.Applicability;
import by.minilooth.vehicleservice.beans.Vehicle;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import by.minilooth.vehicleservice.repositories.*;
import by.minilooth.vehicleservice.services.ApplicabilityService;
import by.minilooth.vehicleservice.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private MakeRepository makeRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private GenerationRepository generationRepository;
    @Autowired private EngineTypeRepository engineTypeRepository;
    @Autowired private TransmissionTypeRepository transmissionTypeRepository;
    @Autowired private BodyTypeRepository bodyTypeRepository;
    @Autowired private ApplicabilityService applicabilityService;

    @Override
    public Vehicle create(Vehicle request) {
        Vehicle vehicle = new Vehicle();

        vehicle.setMake(makeRepository.getReferenceById(request.getMake().getId()));
        vehicle.setModel(modelRepository.getReferenceById(request.getModel().getId()));
        vehicle.setGeneration(generationRepository.getReferenceById(request.getGeneration().getId()));
        vehicle.setEngineType(engineTypeRepository.getReferenceById(request.getEngineType().getId()));
        vehicle.setVolume(request.getVolume());
        vehicle.setPowerHp(request.getPowerHp());
        vehicle.setPowerKw(request.getPowerKw());
        vehicle.setTransmissionType(transmissionTypeRepository.getReferenceById(request.getTransmissionType().getId()));
        vehicle.setBodyType(bodyTypeRepository.getReferenceById(request.getBodyType().getId()));
        vehicle.setStatus(VehicleStatus.ACTIVE);
        vehicle.setModification(request.getModification().trim());

        return save(vehicle);
    }

    @Override
    public Vehicle update(Long id, Vehicle request) throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (stored.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed vehicle with id %s", id));
        }

        stored.setMake(makeRepository.getReferenceById(request.getMake().getId()));
        stored.setModel(modelRepository.getReferenceById(request.getModel().getId()));
        stored.setGeneration(generationRepository.getReferenceById(request.getGeneration().getId()));
        stored.setEngineType(engineTypeRepository.getReferenceById(request.getEngineType().getId()));
        stored.setVolume(request.getVolume());
        stored.setPowerHp(request.getPowerHp());
        stored.setPowerKw(request.getPowerKw());
        stored.setTransmissionType(transmissionTypeRepository.getReferenceById(request.getTransmissionType().getId()));
        stored.setBodyType(bodyTypeRepository.getReferenceById(request.getBodyType().getId()));
        stored.setModification(request.getModification().trim());

        return save(stored);
    }

    @Override
    public List<Vehicle> findAllByProductId(Long productId) {
        return applicabilityService.findAllByProductId(productId).stream()
                .map(Applicability::getVehicle)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAllByStatusNot(VehicleStatus.REMOVED);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle deleteById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (!vehicle.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting vehicle allowed only in REMOVED status");
        }

        vehicleRepository.delete(vehicle);

        return vehicle;
    }

    @Override
    public Vehicle removeById(Long id) throws ObjectNotFoundException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        vehicle.setStatus(VehicleStatus.REMOVED);

        return save(vehicle);
    }

    @Override
    public Vehicle activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (vehicle.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed vehicle with id %s", id));
        }

        vehicle.setStatus(VehicleStatus.ACTIVE);

        return save(vehicle);
    }

    @Override
    public Vehicle deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (vehicle.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed vehicle with id %s", id));
        }

        vehicle.setStatus(VehicleStatus.INACTIVE);

        return save(vehicle);
    }

    private Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

}
