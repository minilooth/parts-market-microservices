package by.minilooth.productservice.mappers.api;

import by.minilooth.productservice.dtos.api.BaseDto;
import by.minilooth.productservice.models.api.BaseEntity;

import java.util.List;

public interface Mapper<E extends BaseEntity, D extends BaseDto> {

    E toEntity(D dto);
    D toDto(E entity);
    List<E> toEntity(List<D> dtos);
    List<D> toDto(List<E> entities);

    default void mapSpecificFields(E source, D destination) {
    }

    default void mapSpecificFields(D source, E destination) {
    }

}