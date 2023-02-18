package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.ManufacturerDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.ManufacturerMapper;
import by.minilooth.productservice.models.Manufacturer;
import by.minilooth.productservice.services.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    @Autowired private ManufacturerService manufacturerService;
    @Autowired private ManufacturerMapper manufacturerMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        List<ManufacturerDto> manufacturerDtos = manufacturerMapper.toDto(manufacturers);
        return ResponseEntity.ok(manufacturerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Manufacturer> optional = manufacturerService.findById(id);
        Optional<ManufacturerDto> optionalDto = optional.map(manufacturer -> manufacturerMapper.toDto(manufacturer));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDto);
        manufacturer = manufacturerService.create(manufacturer);
        manufacturerDto = manufacturerMapper.toDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ManufacturerDto manufacturerDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDto);
        manufacturer = manufacturerService.update(id, manufacturer);
        manufacturerDto = manufacturerMapper.toDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Manufacturer manufacturer = manufacturerService.removeById(id);
        ManufacturerDto manufacturerDto = manufacturerMapper.toDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer manufacturer = manufacturerService.activateById(id);
        ManufacturerDto manufacturerDto = manufacturerMapper.toDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Manufacturer manufacturer = manufacturerService.deactivateById(id);
        ManufacturerDto manufacturerDto = manufacturerMapper.toDto(manufacturer);
        return ResponseEntity.ok(manufacturerDto);
    }

}
