package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.ModificationDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.ModificationMapper;
import by.minilooth.productservice.models.Modification;
import by.minilooth.productservice.services.ModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modification")
public class ModificationController {

    @Autowired private ModificationService modificationService;
    @Autowired private ModificationMapper modificationMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Modification> modifications = modificationService.findAll();
        List<ModificationDto> modificationDtos = modificationMapper.toDto(modifications);
        return ResponseEntity.ok(modificationDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Modification> optional = modificationService.findById(id);
        Optional<ModificationDto> optionalDto = optional.map(modification -> modificationMapper.toDto(modification));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModificationDto modificationDto) {
        Modification modification = modificationMapper.toEntity(modificationDto);
        modification = modificationService.create(modification);
        modificationDto = modificationMapper.toDto(modification);
        return ResponseEntity.ok(modificationDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ModificationDto modificationDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Modification modification = modificationMapper.toEntity(modificationDto);
        modification = modificationService.update(id, modification);
        modificationDto = modificationMapper.toDto(modification);
        return ResponseEntity.ok(modificationDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Modification modification = modificationService.removeById(id);
        ModificationDto modificationDto = modificationMapper.toDto(modification);
        return ResponseEntity.ok(modificationDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Modification modification = modificationService.activateById(id);
        ModificationDto modificationDto = modificationMapper.toDto(modification);
        return ResponseEntity.ok(modificationDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Modification modification = modificationService.deactivateById(id);
        ModificationDto modificationDto = modificationMapper.toDto(modification);
        return ResponseEntity.ok(modificationDto);
    }

}
