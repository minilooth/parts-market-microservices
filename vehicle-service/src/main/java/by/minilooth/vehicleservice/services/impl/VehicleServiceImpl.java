package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.models.Vehicle;
import by.minilooth.vehicleservice.common.enums.VehicleStatus;
import by.minilooth.vehicleservice.repositories.VehicleRepository;
import by.minilooth.vehicleservice.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired private VehicleRepository vehicleRepository;

    @Override
    public Vehicle create(Vehicle entity) {
        Vehicle vehicle = new Vehicle();

        vehicle.setMake(entity.getMake());
        vehicle.setModel(entity.getModel());
        vehicle.setGeneration(entity.getGeneration());
        vehicle.setEngine(entity.getEngine());
        vehicle.setTransmission(entity.getTransmission());
        vehicle.setBody(entity.getBody());
        vehicle.setStatus(VehicleStatus.ACTIVE);

        return save(vehicle);
    }

    @Override
    public Vehicle update(Long id, Vehicle entity) throws ObjectNotFoundException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Vehicle with id %s", id)));

        vehicle.setMake(entity.getMake());
        vehicle.setModel(entity.getModel());
        vehicle.setGeneration(entity.getGeneration());
        vehicle.setEngine(entity.getEngine());
        vehicle.setTransmission(entity.getTransmission());
        vehicle.setBody(entity.getBody());

        return save(vehicle);
    }

    @Override
    public List<Vehicle> findAllActive() {
        return vehicleRepository.findAllByStatus(VehicleStatus.ACTIVE);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle removeById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, VehicleStatus.REMOVED);
    }

    @Override
    public Vehicle activateById(Long id) throws ObjectNotFoundException {
        return updateStatus(id, VehicleStatus.ACTIVE);
    }

    private Vehicle updateStatus(Long id, VehicleStatus status) throws ObjectNotFoundException {
        Vehicle vehicle = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find Vehicle with id %s", id)));

        vehicle.setStatus(status);

        return vehicle;
    }

    private Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

}
