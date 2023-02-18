package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Category;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.ReadService;

public interface CategoryService extends CudService<Category, Long>, ReadService<Category> {

}
