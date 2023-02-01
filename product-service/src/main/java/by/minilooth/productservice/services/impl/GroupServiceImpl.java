package by.minilooth.productservice.services.impl;

import by.minilooth.productservice.common.enums.GroupStatus;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.models.Group;
import by.minilooth.productservice.repositories.GroupRepository;
import by.minilooth.productservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired private GroupRepository groupRepository;

    @Override
    public Group create(Group entity) {
        Group created = new Group();

        created.setName(entity.getName());
        created.setSubcategory(entity.getSubcategory());
        created.setStatus(GroupStatus.ACTIVE);

        return save(created);
    }

    @Override
    public Group update(Long id, Group entity) throws ObjectNotFoundException, ImpossibleActionException {
        Group updated = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find group with id %s", id)));

        if (updated.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to update removed group with id %s", id));
        }

        updated.setName(entity.getName());
        updated.setSubcategory(entity.getSubcategory());

        return save(updated);
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group removeById(Long id) throws ObjectNotFoundException {
        Group group = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find group with id %s", id)));

        group.setStatus(GroupStatus.REMOVED);

        return save(group);
    }

    @Override
    public Group activateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Group group = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find group with id %s", id)));

        if (group.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to activate removed group with id %s", id));
        }

        group.setStatus(GroupStatus.ACTIVE);

        return save(group);
    }

    @Override
    public Group deactivateById(Long id) throws ObjectNotFoundException, ImpossibleActionException {
        Group group = findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Unable to find group with id %s", id)));

        if (group.isEntityRemoved()) {
            throw new ImpossibleActionException(String.format("Unable to deactivate removed group with id %s", id));
        }

        group.setStatus(GroupStatus.INACTIVE);

        return save(group);
    }

    @Override
    public List<Group> findAll(Long subcategoryId) {
        return groupRepository.findAllBySubcategoryIdAndStatusNotOrderByName(subcategoryId, GroupStatus.REMOVED);
    }

    private Group save(Group group) {
        return groupRepository.save(group);
    }

}
