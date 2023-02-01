package by.minilooth.productservice.repositories;

import by.minilooth.productservice.common.enums.GroupStatus;
import by.minilooth.productservice.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findAllBySubcategoryIdAndStatusNotOrderByName(Long id, GroupStatus status);

}
