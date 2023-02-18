package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.GenerationDto;
import by.minilooth.vehicleservice.exceptions.VehicleApiException;
import by.minilooth.vehicleservice.mappers.GenerationMapper;
import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.services.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generation")
public class GenerationController {

    private final GenerationService generationService;
    private final GenerationMapper generationMapper;

    @Autowired
    public GenerationController(GenerationService generationService,
                                GenerationMapper generationMapper) {
        this.generationService = generationService;
        this.generationMapper = generationMapper;
    }

    @GetMapping("/all/{modelId}")
    public ResponseEntity<?> getAll(@PathVariable Long modelId) {
        List<Generation> generations = generationService.findAll(modelId);
        List<GenerationDto> generationDtos = generationMapper.toDto(generations);
        return ResponseEntity.ok(generationDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Generation> generation = generationService.findById(id);
        Optional<GenerationDto> generationDto = generation.map(generationMapper::toDto);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GenerationDto generationDto) throws VehicleApiException {
        Generation generation = generationMapper.toEntity(generationDto);
        generation = generationService.create(generation);
        generationDto = generationMapper.toDto(generation);
        return ResponseEntity.status(HttpStatus.CREATED).body(generationDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GenerationDto generationDto)
            throws VehicleApiException {
        Generation generation = generationMapper.toEntity(generationDto);
        generation = generationService.update(id, generation);
        generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws VehicleApiException {
        Generation generation = generationService.deleteById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws VehicleApiException {
        Generation generation = generationService.removeById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws VehicleApiException {
        Generation generation = generationService.activateById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws VehicleApiException {
        Generation generation = generationService.deactivateById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

}
