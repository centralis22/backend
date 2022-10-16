package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.security.UserAuthService;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginHandler implements RequestHandler {

    private UserAuthService uas;

    /**
     * Login. See authenticateUser().
     * TODO: Add constraints for team names.
     * TODO: Add ability for instructor to remove teams.
     */
    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        Map<String, Object> loginContent = (Map<String, Object>)content;

        try {
            int respond = uas.authenticateUser(
                    user,
                    (String) loginContent.get("user_type"),
                    (String) loginContent.get("user_name"),
                    (String) loginContent.get("user_pswd"),
                    Integer.parseInt((String) loginContent.get("session_id"))
            );
            rre.setStatusCode(respond);
        }
        catch(NumberFormatException nfe) {
            rre
                    .setStatusCode(400)
                    .setStatusMessage("Session ID error.");
        }
    }

    @Autowired
    public void setUas(UserAuthService uas) {
        this.uas = uas;
    }
}
