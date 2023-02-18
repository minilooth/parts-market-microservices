package by.minilooth.productservice.controllers;

import by.minilooth.productservice.dtos.GroupDto;
import by.minilooth.productservice.exceptions.ImpossibleActionException;
import by.minilooth.productservice.exceptions.ObjectNotFoundException;
import by.minilooth.productservice.mappers.GroupMapper;
import by.minilooth.productservice.models.Group;
import by.minilooth.productservice.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired private GroupService groupService;
    @Autowired private GroupMapper groupMapper;

    @GetMapping("/all/{subcategoryId}")
    public ResponseEntity<?> getAllBySubcategoryId(@PathVariable Long subcategoryId) {
        List<Group> groups = groupService.findAll(subcategoryId);
        List<GroupDto> groupDtos = groupMapper.toDto(groups);
        return ResponseEntity.ok(groupDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Group> optional = groupService.findById(id);
        Optional<GroupDto> optionalDto = optional.map(group -> groupMapper.toDto(group));
        return ResponseEntity.ok(optionalDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.toEntity(groupDto);
        group = groupService.create(group);
        groupDto = groupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GroupDto groupDto)
            throws ObjectNotFoundException, ImpossibleActionException {
        Group group = groupMapper.toEntity(groupDto);
        group = groupService.update(id, group);
        groupDto = groupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Long id) throws ObjectNotFoundException {
        Group group = groupService.removeById(id);
        GroupDto groupDto = groupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<?> activateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Group group = groupService.activateById(id);
        GroupDto groupDto = groupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateById(@PathVariable Long id)
            throws ObjectNotFoundException, ImpossibleActionException {
        Group group = groupService.deactivateById(id);
        GroupDto groupDto = groupMapper.toDto(group);
        return ResponseEntity.ok(groupDto);
    }

}
