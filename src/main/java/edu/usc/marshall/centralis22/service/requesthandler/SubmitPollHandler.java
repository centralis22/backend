package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.model.Survey;
import edu.usc.marshall.centralis22.model.Team;
import edu.usc.marshall.centralis22.repository.SurveyRepository;
import edu.usc.marshall.centralis22.repository.TeamRepository;
import edu.usc.marshall.centralis22.security.UserPersistenceService;
import edu.usc.marshall.centralis22.util.BroadcastEntity;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

@Service
public class SubmitPollHandler implements AbstractRequestHandler {

    private SurveyRepository survr;
    private TeamRepository teamr;

    private UserPersistenceService ups;

    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {

        final Logger logger = LoggerFactory.getLogger(SubmitPollHandler.class);

        Map<String, Object> spContent = (Map<String, Object>)content;
        int pollNumber = Integer.parseInt((String) spContent.get("poll_no"));
        List<String> pollContent = (List<String>)spContent.get("poll_response");

        // If current team has submitted a survey, update it. Else, create new.
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

        logger.debug("Session " + user.getSessionId() + " user " + user.getUserName()
                + " submitted survey");

        // TODO: Update to allow any number of cases.
        survey.setQ1(pollContent.get(0));
        survey.setQ2(pollContent.get(1));
        survey.setQ3(pollContent.get(2));
        survey.setQ4(pollContent.get(3));
        survey.setQ5(pollContent.get(4));
        survr.save(survey);

        rre.setStatusCode(200);

        BroadcastEntity bre = new BroadcastEntity(
                "survey_progress",
                List.of(pollNumber, user.getUserName())
        );
        List<SimUser> sessionUsers = ups.getAllUsersInSession(user.getSessionId());
        for(SimUser sessionUser : sessionUsers) {
            if(sessionUser.isInstructor()) {
                WebSocketSession wsAPI = sessionUser.getWebSocketAPISession();
                try {
                    wsAPI.sendMessage(new TextMessage(bre.toString()));
                    logger.debug(wsAPI.getId() + " broadcast" + bre);
                }
                catch(IOException ioe) {
                    ioe.printStackTrace(System.err);
                    rre.setStatusCode(500);
                }
            }
        }
    }

    @Autowired
    public void setSurvr(SurveyRepository survr) {
        this.survr = survr;
    }

    @Autowired
    public void setTeamr(TeamRepository teamr) {
        this.teamr = teamr;
    }

    @Autowired
    public void setUps(UserPersistenceService ups) {
        this.ups = ups;
    }
}
