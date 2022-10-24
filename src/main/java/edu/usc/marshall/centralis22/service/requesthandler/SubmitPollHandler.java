package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.model.Survey;
import edu.usc.marshall.centralis22.model.Team;
import edu.usc.marshall.centralis22.repository.SurveyRepository;
import edu.usc.marshall.centralis22.repository.TeamRepository;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubmitPollHandler implements AbstractRequestHandler {

    private SurveyRepository survr;
    private TeamRepository teamr;

    static final int NUM_QUESTIONS = 5;

    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        Map<String, Object> spContent = (Map<String, Object>)content;

        int pollNumber = (int)spContent.get("poll_no");
        List<String> pollContent = (List<String>)spContent.get("poll_response");

        // Check if the current team has already submitted a survey,
        // If so, update. Else, create new.
        Team team = teamr.findBySeidAndTeamName(
                user.getSessionId(),
                user.getUserName()
        );
        Survey survey = survr.findBySeidAndTmidAndSvgrp(
                user.getSessionId(),
                team.getTmid(),
                pollNumber
        );
        if(survey == null)
        {
            survey = new Survey(42, team.getTmid(), user.getSessionId(), pollNumber);
        }

        survey.setQ1(pollContent.get(0));
        survey.setQ2(pollContent.get(1));
        survey.setQ3(pollContent.get(2));
        survey.setQ4(pollContent.get(3));
        survey.setQ5(pollContent.get(4));

        rre.setStatusCode(200);
    }

    @Autowired
    public void setSurvr(SurveyRepository survr) {
        this.survr = survr;
    }

    @Autowired
    public void setTeamr(TeamRepository teamr) {
        this.teamr = teamr;
    }
}
