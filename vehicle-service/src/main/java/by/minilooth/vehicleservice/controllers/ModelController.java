package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.ModelDto;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.mappers.ModelEntityMapper;
import by.minilooth.vehicleservice.beans.Model;
import by.minilooth.vehicleservice.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired private ModelService modelService;
    @Autowired private ModelEntityMapper modelMapper;

    @GetMapping("/all/{makeId}")
    public ResponseEntity<?> getAll(@PathVariable Long makeId) {
        List<Model> models = modelService.findAll(makeId);
        List<ModelDto> modelDtos = modelMapper.toDto(models);
        return ResponseEntity.ok(modelDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Model> model = modelService.findById(id);
        Optional<ModelDto> modelDto = model.map(entity -> modelMapper.toDto(entity));
        return ResponseEntity.ok(modelDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModelDto modelDto) throws ObjectNotFoundException {
        Model model = modelMapper.toEntity(modelDto);
        model = modelService.create(model);
        modelDto = modelMapper.toDto(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ModelDto modelDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Model model = modelMapper.toEntity(modelDto);
        model = modelService.update(id, model);
        modelDto = modelMapper.toDto(model);
        return ResponseEntity.ok(modelDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Model model = modelService.deleteById(id);
        ModelDto modelDto = modelMapper.toDto(model);
        return ResponseEntity.ok(modelDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Model model = modelService.removeById(id);
        ModelDto modelDto = modelMapper.toDto(model);
        return ResponseEntity.ok(modelDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Model model = modelService.activateById(id);
        ModelDto modelDto = modelMapper.toDto(model);
        return ResponseEntity.ok(modelDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Model model = modelService.deactivateById(id);
        ModelDto modelDto = modelMapper.toDto(model);
        return ResponseEntity.ok(modelDto);
    }

}
