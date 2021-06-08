package thyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thyme.model.Auditorium;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Integer> {

}
