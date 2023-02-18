package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.beans.Applicability;
import by.minilooth.vehicleservice.beans.Vehicle;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
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

    private final VehicleRepository vehicleRepository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final GenerationRepository generationRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final TransmissionTypeRepository transmissionTypeRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final ApplicabilityService applicabilityService;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository,
                              MakeRepository makeRepository,
                              ModelRepository modelRepository,
                              GenerationRepository generationRepository,
                              EngineTypeRepository engineTypeRepository,
                              TransmissionTypeRepository transmissionTypeRepository,
                              BodyTypeRepository bodyTypeRepository,
                              ApplicabilityService applicabilityService) {
        this.vehicleRepository = vehicleRepository;
        this.makeRepository = makeRepository;
        this.modelRepository = modelRepository;
        this.generationRepository = generationRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.transmissionTypeRepository = transmissionTypeRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.applicabilityService = applicabilityService;
    }

    @Override
    public Vehicle create(Vehicle request) throws VehicleApiException {
        Vehicle vehicle = new Vehicle();

        if (!makeRepository.existsById(request.getMake().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find make with id %s",
                    request.getMake().getId()));
        }
        if (!modelRepository.existsById(request.getModel().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find model with id %s",
                    request.getModel().getId()));
        }
        if (!modelRepository.existsByIdAndMakeId(request.getModel().getId(), request.getMake().getId())) {
            throw new ImpossibleActionException(String.format("Model with id %s is not linked to make with id %s",
                    request.getModel().getId(), request.getMake().getId()));
        }
        if (!generationRepository.existsById(request.getGeneration().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find generation with id %s",
                    request.getGeneration().getId()));
        }
        if (!generationRepository.existsByIdAndModelId(request.getGeneration().getId(), request.getModel().getId())) {
            throw new ImpossibleActionException(String.format("Generation with id %s is not linked to model with id %s",
                    request.getGeneration().getId(), request.getModel().getId()));
        }
        if (!engineTypeRepository.existsById(request.getEngineType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find engine type with id %s",
                    request.getEngineType().getId()));
        }
        if (!transmissionTypeRepository.existsById(request.getTransmissionType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find transmission type with id %s",
                    request.getTransmissionType().getId()));
        }
        if (!bodyTypeRepository.existsById(request.getBodyType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find body type with id %s",
                    request.getBodyType().getId()));
        }

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
    public Vehicle update(Long id, Vehicle request) throws VehicleApiException {
        Vehicle stored = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (!makeRepository.existsById(request.getMake().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find make with id %s",
                    request.getMake().getId()));
        }
        if (!modelRepository.existsById(request.getModel().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find model with id %s",
                    request.getModel().getId()));
        }
        if (!modelRepository.existsByIdAndMakeId(request.getModel().getId(), request.getMake().getId())) {
            throw new ImpossibleActionException(String.format("Model with id %s is not linked to make with id %s",
                    request.getModel().getId(), request.getMake().getId()));
        }
        if (!generationRepository.existsById(request.getGeneration().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find generation with id %s",
                    request.getGeneration().getId()));
        }
        if (!generationRepository.existsByIdAndModelId(request.getGeneration().getId(), request.getModel().getId())) {
            throw new ImpossibleActionException(String.format("Generation with id %s is not linked to model with id %s",
                    request.getGeneration().getId(), request.getModel().getId()));
        }
        if (!engineTypeRepository.existsById(request.getEngineType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find engine type with id %s",
                    request.getEngineType().getId()));
        }
        if (!transmissionTypeRepository.existsById(request.getTransmissionType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find transmission type with id %s",
                    request.getTransmissionType().getId()));
        }
        if (!bodyTypeRepository.existsById(request.getBodyType().getId())) {
            throw new ObjectNotFoundException(String.format("Unable to find body type with id %s",
                    request.getBodyType().getId()));
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
    public Vehicle deleteById(Long id) throws VehicleApiException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (!vehicle.isEntityRemoved()) {
            throw new ImpossibleActionException("Deleting vehicle allowed only in REMOVED status");
        }

        vehicleRepository.delete(vehicle);

        return vehicle;
    }

    @Override
    public Vehicle removeById(Long id) throws VehicleApiException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        vehicle.setStatus(VehicleStatus.REMOVED);

        return save(vehicle);
    }

    @Override
    public Vehicle activateById(Long id) throws VehicleApiException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find vehicle with id %s", id)));

        if (vehicle.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed vehicle with id %s", id));
        }

        vehicle.setStatus(VehicleStatus.ACTIVE);

        return save(vehicle);
    }

    @Override
    public Vehicle deactivateById(Long id) throws VehicleApiException {
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
