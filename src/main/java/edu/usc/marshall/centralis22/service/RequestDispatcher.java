package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.service.requesthandler.*;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Wrapper of {@link AbstractRequestHandler}. Sorts each request by type.
 */
@Service
public class RequestDispatcher {

    private final AbstractRequestHandler defaultHandler
            = (user, content, rre) -> rre
            .setStatusCode(400)
            .setContent("No such request.");

    private final AbstractRequestHandler unauthorizedHandler
            = (user, content, rre) -> rre
            .setStatusCode(403)
            .setContent("Require admin privileges or credentials mismatch.");

    private LoginHandler loginHandler;
    private CreateSessionHandler createSessionHandler;
    private AdvanceStageHandler advanceStageHandler;
    private SubmitPollHandler submitPollHandler;
    private DownloadResultsHandler downloadResultsHandler;

    /**
     * Calls the corresponding {@link AbstractRequestHandler} implementation based on
     * {@code request}. The data must be in compliance with the API. If not,
     * an exception will be thrown, failing the request.
     *
     * @param user
     * @param data JSON object provided by the frontend.
     * @param rre Return response to a request.
     */
    public void dispatch(SimUser user, Map<String, Object> data, RequestResponseEntity rre) {

        AbstractRequestHandler requestHandler;

        String request = (String)data.get("request");
        Object content = data.get("content");

        switch(request) {
            case "login":
                requestHandler = loginHandler;
                break;
            case "create_session":
                // create_session does not require the user to be logged in,
                // but passes in credentials as parameters
                requestHandler = createSessionHandler;
                break;
            case "advance_stage":
                requestHandler = requireInstructor(user, advanceStageHandler);
                break;
            case "submit_poll":
                requestHandler = submitPollHandler;
                break;
            case "download_results":
                requestHandler= downloadResultsHandler;
                break;
            default:
                requestHandler = defaultHandler;
                break;
        }
        requestHandler.handle(user, content, rre);
    }

    public AbstractRequestHandler requireInstructor(SimUser user, AbstractRequestHandler handler) {
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

    @Autowired
    public void setSubmitPollHandler(SubmitPollHandler submitPollHandler) {
        this.submitPollHandler = submitPollHandler;
    }

    @Autowired
    public void setDownloadResultsHandler(DownloadResultsHandler downloadResultsHandler) {
        this.downloadResultsHandler = downloadResultsHandler;
    }
}
