package edu.usc.marshall.centralis22.repository;

import edu.usc.marshall.centralis22.model.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Session findBySeid(int sessionId);
}
