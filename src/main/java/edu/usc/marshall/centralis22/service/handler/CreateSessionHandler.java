package edu.usc.marshall.centralis22.service.handler;

import edu.usc.marshall.centralis22.util.RequestResponseEntity;

public class CreateSessionHandler implements RequestHandler {

    @Override
    public void handle(Object content, RequestResponseEntity rre) {
        rre.csetStatusCode(200);
        rre.csetContent(199810);
    }
}
