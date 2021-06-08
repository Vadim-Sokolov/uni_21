package thyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thyme.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

}
