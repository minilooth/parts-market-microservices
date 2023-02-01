package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Subcategory;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.NestedReadService;

public interface SubcategoryService extends CudService<Subcategory>, NestedReadService<Subcategory, Long> {
}
