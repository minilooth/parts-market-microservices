package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.repositories.CharacteristicRepository;
import by.minilooth.productservice.services.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired private CharacteristicRepository characteristicRepository;

}
