package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.service.requesthandler.AdvanceStageHandler;
import edu.usc.marshall.centralis22.service.requesthandler.CreateSessionHandler;
import edu.usc.marshall.centralis22.service.requesthandler.LoginHandler;
import edu.usc.marshall.centralis22.service.requesthandler.RequestHandler;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Wrapper of {@link RequestHandler}. Sorts each request by type.
 */
@Service
public class RequestDispatcher {

    private final RequestHandler defaultHandler
            = (user, content, rre) -> rre
            .setStatusCode(400)
            .setContent("No such request.");

    private final RequestHandler unauthorizedHandler
            = (user, content, rre) -> rre
            .setStatusCode(403)
            .setContent("Require admin privileges or credentials mismatch.");

    private LoginHandler loginHandler;
    private CreateSessionHandler createSessionHandler;
    private AdvanceStageHandler advanceStageHandler;

    /**
     * Calls the corresponding {@link RequestHandler} implementation based on
     * {@code request}. The data must be in compliance with the API. If not,
     * an exception will be thrown, failing the request.
     *
     * @param user
     * @param data JSON object provided by the frontend.
     * @param rre Return response to a request.
     */
    public void dispatch(SimUser user, Map<String, Object> data, RequestResponseEntity rre) {

        RequestHandler requestHandler;

        String request = (String)data.get("request");
        Object content = data.get("content");

        switch(request) {
            case "login":
                requestHandler = loginHandler;
                break;
            case "create_session":
                requestHandler = createSessionHandler;
                break;
            case "advance_stage":
                requestHandler = requireInstructor(user, advanceStageHandler);
                break;
            default:
                requestHandler = defaultHandler;
                break;
        }
        requestHandler.handle(user, content, rre);
    }

    public RequestHandler requireInstructor(SimUser user, RequestHandler handler) {
        return user.isInstructor() ? handler : unauthorizedHandler;
    }

    @Autowired
    public void setLoginHandler(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @Autowired
    public void setCreateSessionHandler(CreateSessionHandler createSessionHandler) {
        this.createSessionHandler = createSessionHandler;
    }

    @Autowired
    public void setAdvanceStageHandler(AdvanceStageHandler advanceStageHandler) {
        this.advanceStageHandler = advanceStageHandler;
    }
}
