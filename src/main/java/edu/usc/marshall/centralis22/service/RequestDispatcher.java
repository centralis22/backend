package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Wrapper of {@link RequestHandler}.
 */
@Service
public class RequestDispatcher {

    /**
     * Calls the corresponding {@link RequestHandler} implementation based on
     * {@code request}.
     *
     * @param request See API.
     * @param content See API.
     * @param rre See API.
     */
    public void dispatch(String request, Object content, RequestResponseEntity rre) {

        RequestHandler requestHandler;

        switch(request) {
            case "advance_stage":
                requestHandler = new AdvanceStageHandler();
                break;
            default:
                requestHandler = (contentDummy, areDummy) -> {};
                break;
        }

        requestHandler.handle(content, rre);
    }
}
