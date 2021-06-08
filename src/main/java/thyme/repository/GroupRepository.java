package thyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thyme.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
