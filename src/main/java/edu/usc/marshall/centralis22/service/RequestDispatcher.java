package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.service.handler.CreateSessionHandler;
import edu.usc.marshall.centralis22.service.handler.LoginHandler;
import edu.usc.marshall.centralis22.service.handler.RequestHandler;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Wrapper of {@link RequestHandler}.
 */
@Service
public class RequestDispatcher {

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
                requestHandler = new LoginHandler();
                break;
            case "create_session":
                requestHandler = new CreateSessionHandler();
                break;
            default:
                requestHandler = (contentDummy, rreDummy) -> {};
                break;
        }

        requestHandler.handle(content, rre);
    }
}
