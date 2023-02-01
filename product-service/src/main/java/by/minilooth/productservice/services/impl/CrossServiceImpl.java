package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.repositories.CrossRepository;
import by.minilooth.productservice.services.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CrossServiceImpl implements CrossService {

    @Autowired private CrossRepository crossRepository;

}
