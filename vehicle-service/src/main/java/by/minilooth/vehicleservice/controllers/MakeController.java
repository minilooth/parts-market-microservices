package by.minilooth.vehicleservice.controllers;

import by.minilooth.vehicleservice.dtos.MakeDto;
import by.minilooth.vehicleservice.exceptions.ImpossibleActionException;
import by.minilooth.vehicleservice.exceptions.ObjectNotFoundException;
import by.minilooth.vehicleservice.mappers.MakeMapper;
import by.minilooth.vehicleservice.beans.Make;
import by.minilooth.vehicleservice.services.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/make")
public class MakeController {

    @Autowired private MakeService makeService;
    @Autowired private MakeMapper makeMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Make> makes = makeService.findAll();
        List<MakeDto> makeDtos = makeMapper.toDto(makes);
        return ResponseEntity.ok(makeDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Make> make = makeService.findById(id);
        Optional<MakeDto> makeDto = make.map(entity -> makeMapper.toDto(entity));
        return ResponseEntity.ok(makeDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MakeDto makeDto) {
        Make make = makeMapper.toEntity(makeDto);
        make = makeService.create(make);
        makeDto = makeMapper.toDto(make);
        return ResponseEntity.status(HttpStatus.CREATED).body(makeDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakeDto makeDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Make make = makeMapper.toEntity(makeDto);
        make = makeService.update(id, make);
        makeDto = makeMapper.toDto(make);
        return ResponseEntity.ok(makeDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Make make = makeService.deleteById(id);
        MakeDto makeDto = makeMapper.toDto(make);
        return ResponseEntity.ok(makeDto);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Make make = makeService.removeById(id);
        MakeDto makeDto = makeMapper.toDto(make);
        return ResponseEntity.ok(makeDto);
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Make make = makeService.activateById(id);
        MakeDto makeDto = makeMapper.toDto(make);
        return ResponseEntity.ok(makeDto);
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Make make = makeService.deactivateById(id);
        MakeDto makeDto = makeMapper.toDto(make);
        return ResponseEntity.ok(makeDto);
    }

}
