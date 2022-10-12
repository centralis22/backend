package edu.usc.marshall.centralis22.repository;

import edu.usc.marshall.centralis22.model.SimSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<SimSession, Integer> {
    SimSession findBySeid(int sessionId);

    /**
     * Returns a list of all seids.
     */
    @Query("SELECT p.seid from #{#entityName} p")
    List<Integer> getAllSeid();
}
