package by.minilooth.productservice.services.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilteringPagingReadService<E, F> {

    Page<E> findAll(F filter, Pageable pageable);

}
