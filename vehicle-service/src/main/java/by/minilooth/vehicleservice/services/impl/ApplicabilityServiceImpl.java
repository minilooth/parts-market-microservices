package by.minilooth.vehicleservice.services.impl;

import by.minilooth.vehicleservice.beans.Applicability;
import by.minilooth.vehicleservice.repositories.ApplicabilityRepository;
import by.minilooth.vehicleservice.services.ApplicabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ApplicabilityServiceImpl implements ApplicabilityService {

    @Autowired private ApplicabilityRepository applicabilityRepository;

    @Override
    public List<Applicability> findAllByProductId(Long productId) {
        return applicabilityRepository.findAllByIdProductId(productId);
    }

}
