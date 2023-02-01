package by.minilooth.productservice.services;

import by.minilooth.productservice.models.Group;
import by.minilooth.productservice.services.api.CudService;
import by.minilooth.productservice.services.api.NestedReadService;

public interface GroupService extends CudService<Group>, NestedReadService<Group, Long> {
}
