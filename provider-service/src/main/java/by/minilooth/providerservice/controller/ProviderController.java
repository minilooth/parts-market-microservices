package by.minilooth.providerservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok("Get all providers endpoint, port=");
    }

}
