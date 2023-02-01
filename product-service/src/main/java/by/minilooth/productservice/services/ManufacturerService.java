package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Manufacturer;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.ReadService;

public interface ManufacturerService extends CudService<Manufacturer>, ReadService<Manufacturer> {

}
