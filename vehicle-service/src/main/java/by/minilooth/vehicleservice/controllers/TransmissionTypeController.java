package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.beans.TransmissionType;
import by.minilooth.vehicleservice.dtos.TransmissionTypeDto;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.mappers.TransmissionTypeMapper;
import by.minilooth.vehicleservice.services.TransmissionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transmission-type")
public class TransmissionTypeController {

    private final TransmissionTypeService transmissionTypeService;
    private final TransmissionTypeMapper transmissionTypeMapper;

    @Autowired
    public TransmissionTypeController(TransmissionTypeService transmissionTypeService,
                                      TransmissionTypeMapper transmissionTypeMapper) {
        this.transmissionTypeService = transmissionTypeService;
        this.transmissionTypeMapper = transmissionTypeMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<TransmissionType> transmissionTypes = transmissionTypeService.findAll();
        List<TransmissionTypeDto> transmissionTypeDtos = transmissionTypeMapper.toDto(transmissionTypes);
        return ResponseEntity.ok(transmissionTypeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<TransmissionType> transmissionType = transmissionTypeService.findById(id);
        Optional<TransmissionTypeDto> transmissionTypeDto = transmissionType.map(transmissionTypeMapper::toDto);
        return ResponseEntity.ok(transmissionTypeDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransmissionTypeDto transmissionTypeDto) {
        TransmissionType transmissionType = transmissionTypeMapper.toEntity(transmissionTypeDto);
        transmissionType = transmissionTypeService.create(transmissionType);
        transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.status(HttpStatus.CREATED).body(transmissionTypeDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TransmissionTypeDto transmissionTypeDto)
            throws VehicleApiException {
        TransmissionType transmissionType = transmissionTypeMapper.toEntity(transmissionTypeDto);
        transmissionType = transmissionTypeService.update(id, transmissionType);
        transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.ok(transmissionTypeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws VehicleApiException {
        TransmissionType transmissionType = transmissionTypeService.deleteById(id);
        TransmissionTypeDto transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.ok(transmissionTypeDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws VehicleApiException {
        TransmissionType transmissionType = transmissionTypeService.removeById(id);
        TransmissionTypeDto transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.ok(transmissionTypeDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws VehicleApiException {
        TransmissionType transmissionType = transmissionTypeService.activateById(id);
        TransmissionTypeDto transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.ok(transmissionTypeDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws VehicleApiException {
        TransmissionType transmissionType = transmissionTypeService.deactivateById(id);
        TransmissionTypeDto transmissionTypeDto = transmissionTypeMapper.toDto(transmissionType);
        return ResponseEntity.ok(transmissionTypeDto);
    }

}
