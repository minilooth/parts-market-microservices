package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Modification;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.ReadService;

public interface ModificationService extends CudService<Modification>, ReadService<Modification> {
}
