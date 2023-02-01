package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.GenerationDto;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.mappers.GenerationMapper;
import by.minilooth.vehicleservice.models.Generation;
import by.minilooth.vehicleservice.services.GenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generation")
public class GenerationController {

    @Autowired private GenerationService generationService;
    @Autowired private GenerationMapper generationMapper;

    @GetMapping("/active/{modelId}")
    public ResponseEntity<?> getAllActive(@PathVariable Long modelId) {
        List<Generation> generations = generationService.findAllActive(modelId);
        List<GenerationDto> generationDtos = generationMapper.toDto(generations);
        return ResponseEntity.ok(generationDtos);
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
        Optional<GenerationDto> generationDto = generation.map(entity -> generationMapper.toDto(entity));
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GenerationDto generationDto) {
        Generation generation = generationMapper.toEntity(generationDto);
        generation = generationService.create(generation);
        generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GenerationDto generationDto)
            throws ObjectNotFoundException {
        Generation generation = generationMapper.toEntity(generationDto);
        generation = generationService.update(id, generation);
        generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id) throws ObjectNotFoundException {
        Generation generation = generationService.activateById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Generation generation = generationService.removeById(id);
        GenerationDto generationDto = generationMapper.toDto(generation);
        return ResponseEntity.ok(generationDto);
    }

}
