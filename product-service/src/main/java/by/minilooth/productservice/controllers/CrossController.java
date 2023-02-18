package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.CrossDto;
import by.minilooth.productservice.mappers.CrossMapper;
import by.minilooth.productservice.models.Cross;
import by.minilooth.productservice.services.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cross")
public class CrossController {

    @Autowired private CrossService crossService;
    @Autowired private CrossMapper crossMapper;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getAllByProductId(@PathVariable Long productId) {
        List<Cross> crosses = crossService.findAllByProductId(productId);
        List<CrossDto> crossDtos = crossMapper.toDto(crosses);
        return ResponseEntity.ok(crossDtos);
    }

}
