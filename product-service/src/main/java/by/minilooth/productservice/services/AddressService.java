package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Address;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.ReadService;

public interface AddressService extends CudService<Address>, ReadService<Address> {

}
