package edu.usc.marshall.centralis22.service.handler;

import edu.usc.marshall.centralis22.security.UserPersistenceService;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginHandler implements RequestHandler {

    @Autowired
    private UserPersistenceService ups;

    @Override
    public void handle(Object content, RequestResponseEntity rre) {
        Map<String, Object> loginContent = (Map<String, Object>)content;



        rre.csetStatusCode(300);
    }
}
