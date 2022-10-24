package edu.usc.marshall.centralis22.repository;

import edu.usc.marshall.centralis22.model.Instructor;
import edu.usc.marshall.centralis22.model.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Integer> {
    /**
     * A survey requires 3 identifiers:
     * The team's ID. The team's session's ID. The survey number, 1 or 2.
     */
    Survey findBySeidAndTmidAndSvgrp(int seid, int tmid, int svgrp);
}
