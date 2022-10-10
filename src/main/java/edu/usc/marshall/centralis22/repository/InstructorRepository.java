package edu.usc.marshall.centralis22.repository;

import edu.usc.marshall.centralis22.model.Instructor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Instructor findByUserName(String username);
}
