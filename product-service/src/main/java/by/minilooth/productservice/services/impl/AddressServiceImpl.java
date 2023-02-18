package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.AddressStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Address;
import by.minilooth.productservice.repositories.AddressRepository;
import by.minilooth.productservice.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired private AddressRepository addressRepository;

    @Override
    public Address create(Address address) {
        Address created = new Address();

        created.setStreet(address.getStreet());
        created.setPostIndex(address.getPostIndex());
        created.setCity(address.getCity());
        created.setStatus(AddressStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Address update(Long id, Address address) throws ObjectNotFoundException, ImpossibleActionException {
        Address updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find address with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed address with id %s", id));
        }

        updated.setStreet(address.getStreet());
        updated.setPostIndex(address.getPostIndex());
        updated.setCity(address.getCity());

        return save(updated);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAllByStatusNotOrderByStreetDesc(AddressStatus.REMOVED);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address removeById(Long id) throws ObjectNotFoundException {
        Address address = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find address with id %s", id)));

        address.setStatus(AddressStatus.REMOVED);

        return save(address);
    }

    @Override
    public Address activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Address address = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find address with id %s", id)));

        if (address.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed address with id %s", id));
        }

        address.setStatus(AddressStatus.ACTIVE);

        return save(address);
    }

    @Override
    public Address deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Address address = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find address with id %s", id)));

        if (address.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed address with id %s", id));
        }

        address.setStatus(AddressStatus.INACTIVE);

        return save(address);
    }

    private Address save(Address address) {
        return addressRepository.save(address);
    }

}
