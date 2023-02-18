package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.VehicleDto;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
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

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService,
                             VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Vehicle> vehicles = vehicleService.findAll();
        List<VehicleDto> vehicleDtos = vehicleMapper.toDto(vehicles);
        return ResponseEntity.ok(vehicleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Vehicle> vehicle = vehicleService.findById(id);
        Optional<VehicleDto> vehicleDto = vehicle.map(vehicleMapper::toDto);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VehicleDto vehicleDto) throws VehicleApiException {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicle = vehicleService.create(vehicle);
        vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VehicleDto vehicleDto)
            throws VehicleApiException {
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
        vehicle = vehicleService.update(id, vehicle);
        vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws VehicleApiException {
        Vehicle vehicle = vehicleService.deleteById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws VehicleApiException {
        Vehicle vehicle = vehicleService.removeById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws VehicleApiException {
        Vehicle vehicle = vehicleService.activateById(id);
        VehicleDto vehicleDto = vehicleMapper.toDto(vehicle);
        return ResponseEntity.ok(vehicleDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws VehicleApiException {
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
