package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.AddressDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.AddressMapper;
import by.minilooth.productservice.models.Address;
import by.minilooth.productservice.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired private AddressService addressService;
    @Autowired private AddressMapper addressMapper;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Address> addresses = addressService.findAll();
        List<AddressDto> addressDtos = addressMapper.toDto(addresses);
        return ResponseEntity.ok(addressDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Address> optional = addressService.findById(id);
        Optional<AddressDto> optionalDto = optional.map(address -> addressMapper.toDto(address));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        address = addressService.create(address);
        addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AddressDto addressDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Address address = addressMapper.toEntity(addressDto);
        address = addressService.update(id, address);
        addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Address address = addressService.removeById(id);
        AddressDto addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Address address = addressService.activateById(id);
        AddressDto addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Address address = addressService.deactivateById(id);
        AddressDto addressDto = addressMapper.toDto(address);
        return ResponseEntity.ok(addressDto);
    }

}
