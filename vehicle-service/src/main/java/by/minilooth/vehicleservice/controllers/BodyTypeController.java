package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.beans.BodyType;
import by.minilooth.vehicleservice.dtos.BodyTypeDto;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.mappers.BodyTypeMapper;
import by.minilooth.vehicleservice.services.BodyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/body-type")
public class BodyTypeController {

    private final BodyTypeService bodyTypeService;
    private final BodyTypeMapper bodyTypeMapper;

    @Autowired
    public BodyTypeController(BodyTypeService bodyTypeService,
                              BodyTypeMapper bodyTypeMapper) {
        this.bodyTypeService = bodyTypeService;
        this.bodyTypeMapper = bodyTypeMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<BodyType> bodyTypes = bodyTypeService.findAll();
        List<BodyTypeDto> bodyTypeDtos = bodyTypeMapper.toDto(bodyTypes);
        return ResponseEntity.ok(bodyTypeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<BodyType> bodyType = bodyTypeService.findById(id);
        Optional<BodyTypeDto> bodyTypeDto = bodyType.map(bodyTypeMapper::toDto);
        return ResponseEntity.ok(bodyTypeDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BodyTypeDto bodyTypeDto) {
        BodyType bodyType = bodyTypeMapper.toEntity(bodyTypeDto);
        bodyType = bodyTypeService.create(bodyType);
        bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.status(HttpStatus.CREATED).body(bodyTypeDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BodyTypeDto bodyTypeDto)
            throws VehicleApiException {
        BodyType bodyType = bodyTypeMapper.toEntity(bodyTypeDto);
        bodyType = bodyTypeService.update(id, bodyType);
        bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.ok(bodyTypeDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws VehicleApiException {
        BodyType bodyType = bodyTypeService.removeById(id);
        BodyTypeDto bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.ok(bodyTypeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws VehicleApiException {
        BodyType bodyType = bodyTypeService.deleteById(id);
        BodyTypeDto bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.ok(bodyTypeDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws VehicleApiException {
        BodyType bodyType = bodyTypeService.activateById(id);
        BodyTypeDto bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.ok(bodyTypeDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws VehicleApiException {
        BodyType bodyType = bodyTypeService.deactivateById(id);
        BodyTypeDto bodyTypeDto = bodyTypeMapper.toDto(bodyType);
        return ResponseEntity.ok(bodyTypeDto);
    }

}
