package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.AddressStatus;
import by.minilooth.productservice.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByStatusNotOrderByAddressDesc(AddressStatus status);

}
