package by.minilooth.productservice.services.api;

import java.util.List;

public interface NestedReadService<T, ID> {

    List<T> findAll(ID id);

}
