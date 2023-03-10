package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.beans.EngineType;
import by.minilooth.vehicleservice.dtos.EngineTypeDto;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.mappers.EngineTypeMapper;
import by.minilooth.vehicleservice.services.EngineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/engine-type")
public class EngineTypeController {

    private final EngineTypeService engineTypeService;
    private final EngineTypeMapper engineTypeMapper;

    @Autowired
    public EngineTypeController(EngineTypeService engineTypeService,
                                EngineTypeMapper engineTypeMapper) {
        this.engineTypeService = engineTypeService;
        this.engineTypeMapper = engineTypeMapper;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<EngineType> engineTypes = engineTypeService.findAll();
        List<EngineTypeDto> engineTypeDtos = engineTypeMapper.toDto(engineTypes);
        return ResponseEntity.ok(engineTypeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<EngineType> engineType = engineTypeService.findById(id);
        Optional<EngineTypeDto> engineTypeDto = engineType.map(engineTypeMapper::toDto);
        return ResponseEntity.ok(engineTypeDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EngineTypeDto engineTypeDto) {
        EngineType engineType = engineTypeMapper.toEntity(engineTypeDto);
        engineType = engineTypeService.create(engineType);
        engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.status(HttpStatus.CREATED).body(engineTypeDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EngineTypeDto engineTypeDto)
            throws VehicleApiException {
        EngineType engineType = engineTypeMapper.toEntity(engineTypeDto);
        engineType = engineTypeService.update(id, engineType);
        engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.ok(engineTypeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws VehicleApiException {
        EngineType engineType = engineTypeService.deleteById(id);
        EngineTypeDto engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.ok(engineTypeDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws VehicleApiException {
        EngineType engineType = engineTypeService.removeById(id);
        EngineTypeDto engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.ok(engineTypeDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws VehicleApiException {
        EngineType engineType = engineTypeService.activateById(id);
        EngineTypeDto engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.ok(engineTypeDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws VehicleApiException {
        EngineType engineType = engineTypeService.deactivateById(id);
        EngineTypeDto engineTypeDto = engineTypeMapper.toDto(engineType);
        return ResponseEntity.ok(engineTypeDto);
    }

}
