package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.CharacteristicDto;
import by.minilooth.productservice.mappers.CharacteristicMapper;
import by.minilooth.productservice.models.Characteristic;
import by.minilooth.productservice.services.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/characteristic")
public class CharacteristicController {

    @Autowired private CharacteristicService characteristicService;
    @Autowired private CharacteristicMapper characteristicMapper;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getAllByProductId(@PathVariable Long productId) {
        List<Characteristic> characteristics = characteristicService.findAllByProductId(productId);
        List<CharacteristicDto> characteristicDtos = characteristicMapper.toDto(characteristics);
        return ResponseEntity.ok(characteristicDtos);
    }

}
