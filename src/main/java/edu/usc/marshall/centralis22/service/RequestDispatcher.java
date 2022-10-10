package edu.usc.marshall.centralis22.service;

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

    public LoginHandler loginHandler;
    public CreateSessionHandler createSessionHandler;

    /**
     * Calls the corresponding {@link RequestHandler} implementation based on
     * {@code request}. The data must be in compliance with the API. If not,
     * an exception will be thrown, failing the request.
     *
     * @param data JSON object provided by the frontend.
     * @param rre Return response to a request.
     */
    public void dispatch(Map<String, Object> data, RequestResponseEntity rre) {

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
            default:
                requestHandler = (contentDummy, rreDummy) -> {
                    rreDummy
                            .setStatusCode(400)
                            .setStatusMessage("Request type error.");
                };
                break;
        }

        requestHandler.handle(content, rre);
    }

    @Autowired
    public void setLoginHandler(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @Autowired
    public void setCreateSessionHandler(CreateSessionHandler createSessionHandler) {
        this.createSessionHandler = createSessionHandler;
    }
}
