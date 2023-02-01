package by.minilooth.productservice.models.filters;

import java.util.Map;

public class ProductFilter {

    private ProductFilter() {}

    private ProductFilter(Map<String, Object> params) {

    }

    public static ProductFilter fromParameters(Map<String, Object> params) {
        return new ProductFilter(params);
    }

}
