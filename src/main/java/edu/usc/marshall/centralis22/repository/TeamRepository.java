package edu.usc.marshall.centralis22.repository;

import edu.usc.marshall.centralis22.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team findBySeidAndTeamName(int sessionId, String teamName);
}
