package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.ProductDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.ProductMapper;
import by.minilooth.productservice.models.Product;
import by.minilooth.productservice.models.filters.ProductFilter;
import by.minilooth.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired private ProductService productService;
    @Autowired private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Map<String, Object> params, Pageable pageable) {
        Page<Product> products = productService.findAll(ProductFilter.fromParameters(params), pageable);
        Page<ProductDto> productDtos = products.map(product -> productMapper.toDto(product));
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Product> optional = productService.findById(id);
        Optional<ProductDto> optionalDto = optional.map(product -> productMapper.toDto(product));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product = productService.create(product);
        productDto = productMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDto productDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Product product = productMapper.toEntity(productDto);
        product = productService.update(id, product);
        productDto = productMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Product product = productService.removeById(id);
        ProductDto productDto = productMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Product product = productService.activateById(id);
        ProductDto productDto = productMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Product product = productService.deactivateById(id);
        ProductDto productDto = productMapper.toDto(product);
        return ResponseEntity.ok(productDto);
    }

}
