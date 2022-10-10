package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginHandler implements RequestHandler {

    @Override
    public void handle(Object content, RequestResponseEntity rre) {
        Map<String, Object> loginContent = (Map<String, Object>)content;

        /*
        int respond = ups.authenticateUser(
                (String)loginContent.get("user_type"),
                (String)loginContent.get("user_name"),
                (String)loginContent.get("user_pswd"),
                Integer.parseInt((String)loginContent.get("session_id"))
        );*/

        rre.setStatusCode(200);
    }
}
