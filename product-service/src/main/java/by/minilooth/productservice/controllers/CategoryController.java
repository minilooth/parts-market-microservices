package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.CategoryDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.CategoryMapper;
import by.minilooth.productservice.models.Category;
import by.minilooth.productservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired private CategoryService categoryService;
    @Autowired private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDto> categoryDtos = categoryMapper.toDto(categories);
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Category> optional = categoryService.findById(id);
        Optional<CategoryDto> optionalDto = optional.map(category -> categoryMapper.toDto(category));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.create(category);
        categoryDto = categoryMapper.toDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryService.update(id, category);
        categoryDto = categoryMapper.toDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Category category = categoryService.removeById(id);
        CategoryDto categoryDto = categoryMapper.toDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Category category = categoryService.activateById(id);
        CategoryDto categoryDto = categoryMapper.toDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Category category = categoryService.deactivateById(id);
        CategoryDto categoryDto = categoryMapper.toDto(category);
        return ResponseEntity.ok(categoryDto);
    }

}
