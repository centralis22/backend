package edu.usc.marshall.centralis22.service;

import edu.usc.marshall.centralis22.util.RequestResponseEntity;

import java.util.Map;

public class LoginHandler implements RequestHandler {

    @Override
    public void handle(Object content, RequestResponseEntity rre) {
        Map<String, Object> loginContent = (Map<String, Object>)content;
        System.out.println(loginContent.get("user_type"));
        System.out.println(loginContent.get("user_pswd"));
        System.out.println(loginContent.get("session_id"));
    }
}
