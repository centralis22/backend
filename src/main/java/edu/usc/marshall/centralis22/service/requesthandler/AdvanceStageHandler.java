package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimSession;
import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.security.UserPersistenceService;
import edu.usc.marshall.centralis22.util.BroadcastEntity;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Service
public class AdvanceStageHandler implements RequestHandler {

    private SessionRepository sessr;
    private UserPersistenceService ups;

    /**
     * Advances a session's stage. If stage == -1, advance to next stage.
     * Else, advance to specified stage. Broadcasts advancement to all users
     * in session.
     * TODO: Valid stage check.
     */
    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        rre.setStatusCode(200);

        SimSession session = sessr.findBySeid(user.getSessionId());
        int stage = Integer.parseInt((String)content);
        stage = stage == -1 ? session.getStage() + 1 : stage;
        session.setStage(stage);
        sessr.save(session);

        BroadcastEntity bre = new BroadcastEntity("advance_stage", stage);
        List<SimUser> sessionUsers = ups.getAllUsersInSession(user.getSessionId());
        for(SimUser sessionUser : sessionUsers) {
            WebSocketSession wsAPI = sessionUser.getWebSocketAPISession();
            try {
                wsAPI.sendMessage(new TextMessage(bre.toString()));
            }
            catch(IOException ioe) {
                rre.setStatusCode(500);
            }
        }
    }

    @Autowired
    public void setSessr(SessionRepository sessr) {
        this.sessr = sessr;
    }

    @Autowired
    public void setUps(UserPersistenceService ups) {
        this.ups = ups;
    }
}
