package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.VehicleDto;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.mappers.VehicleMapper;
import by.minilooth.vehicleservice.beans.Vehicle;
import by.minilooth.vehicleservice.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired private VehicleService vehicleService;
    @Autowired private VehicleMapper vehicleMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Vehicle> vehicles = vehicleService.findAll();
        List<VehicleDto> vehicleDtos = vehicleMapper.toDto(vehicles);
        return ResponseEntity.ok(vehicleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.findById(id);
        Optional<VehicleDto> vehicleDto = vehicle.map(entity -> vehicleMapper.toDto(entity));
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicle = vehicleService.create(vehicle);
        vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VehicleDto vehicleDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicle = vehicleService.update(id, vehicle);
        vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = vehicleService.deleteById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Vehicle vehicle = vehicleService.removeById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = vehicleService.activateById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Vehicle vehicle = vehicleService.deactivateById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @GetMapping("/applicability/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable Long productId) {
        List<Vehicle> vehicles = vehicleService.findAllByProductId(productId);
        List<VehicleDto> vehicleDtos = vehicleMapper.toDto(vehicles);
        return ResponseEntity.ok(vehicleDtos);
    }

}
