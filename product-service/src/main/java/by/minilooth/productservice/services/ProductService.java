package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Product;
import by.minilooth.productservice.models.filters.ProductFilter;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.FilteringPagingReadService;

public interface ProductService extends CudService<Product>, FilteringPagingReadService<Product, ProductFilter> {
}
