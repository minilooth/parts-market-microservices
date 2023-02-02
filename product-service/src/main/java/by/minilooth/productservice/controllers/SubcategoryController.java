package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.SubcategoryDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.SubcategoryMapper;
import by.minilooth.productservice.models.Subcategory;
import by.minilooth.productservice.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {

    @Autowired private SubcategoryService subcategoryService;
    @Autowired private SubcategoryMapper subcategoryMapper;

    @GetMapping("/all/{categoryId}")
    public ResponseEntity<?> getAllByCategoryId(@PathVariable Long categoryId) {
        List<Subcategory> subcategories = subcategoryService.findAll(categoryId);
        List<SubcategoryDto> subcategoryDtos = subcategoryMapper.toDto(subcategories);
        return ResponseEntity.ok(subcategoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Subcategory> optional = subcategoryService.findById(id);
        Optional<SubcategoryDto> optionalDto = optional.map(subcategory -> subcategoryMapper.toDto(subcategory));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SubcategoryDto subcategoryDto) {
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        subcategory = subcategoryService.create(subcategory);
        subcategoryDto = subcategoryMapper.toDto(subcategory);
        return ResponseEntity.ok(subcategoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SubcategoryDto subcategoryDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = subcategoryMapper.toEntity(subcategoryDto);
        subcategory = subcategoryService.update(id, subcategory);
        subcategoryDto = subcategoryMapper.toDto(subcategory);
        return ResponseEntity.ok(subcategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Subcategory subcategory = subcategoryService.removeById(id);
        SubcategoryDto subcategoryDto = subcategoryMapper.toDto(subcategory);
        return ResponseEntity.ok(subcategoryDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = subcategoryService.activateById(id);
        SubcategoryDto subcategoryDto = subcategoryMapper.toDto(subcategory);
        return ResponseEntity.ok(subcategoryDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Subcategory subcategory = subcategoryService.deactivateById(id);
        SubcategoryDto subcategoryDto = subcategoryMapper.toDto(subcategory);
        return ResponseEntity.ok(subcategoryDto);
    }

}
