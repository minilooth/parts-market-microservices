package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.CityDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.CityMapper;
import by.minilooth.productservice.models.City;
import by.minilooth.productservice.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired private CityService cityService;
    @Autowired private CityMapper cityMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<City> cities = cityService.findAll();
        List<CityDto> cityDtos = cityMapper.toDto(cities);
        return ResponseEntity.ok(cityDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<City> optional = cityService.findById(id);
        Optional<CityDto> optionalDto = optional.map(city -> cityMapper.toDto(city));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CityDto cityDto) {
        City city = cityMapper.toEntity(cityDto);
        city = cityService.create(city);
        cityDto = cityMapper.toDto(city);
        return ResponseEntity.ok(cityDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CityDto cityDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        City city = cityMapper.toEntity(cityDto);
        city = cityService.update(id, city);
        cityDto = cityMapper.toDto(city);
        return ResponseEntity.ok(cityDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        City city = cityService.removeById(id);
        CityDto cityDto = cityMapper.toDto(city);
        return ResponseEntity.ok(cityDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        City city = cityService.activateById(id);
        CityDto cityDto = cityMapper.toDto(city);
        return ResponseEntity.ok(cityDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        City city = cityService.deactivateById(id);
        CityDto cityDto = cityMapper.toDto(city);
        return ResponseEntity.ok(cityDto);
    }

}
