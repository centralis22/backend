package edu.usc.marshall.centralis22.security;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Temporary object to store user information.
 */
@Component
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserPersistenceSvc {
    private String userName = "unauthorized1";
    private String userType = "unauthorized";
    private int sessionId = 0;

    public void authenticateUser(
            String userType,
            String userName,
            String userPswd,
            int sessionId) {

    }

    public boolean isAdmin() {
        return userType.equals("admin");
    }

    public boolean isStudent() {
        return userType.equals("student");
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
