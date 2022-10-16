package edu.usc.marshall.centralis22.model;

import org.springframework.web.socket.WebSocketSession;

/**
 * A {@code SimUser} is any user, {@link Instructor} or {@link Team},
 * that is connected to the server through the "/api" endpoint.
 */
public class SimUser {
    private String userName;
    private String userType;
    /**
     * Simulation session ID. A team is associated with one session.
     * An instructor may freely join any session, but is associated with
     * at most one session at a time. An instructor has the option to not
     * join any session if creating a new session.
     */
    private int sessionId;
    /**
     * WebSocket session.
     */
    private WebSocketSession wsAPI;

    public String getUserName() {
        return userName;
    }

    public int getSessionId() {
        return sessionId;
    }

    public boolean isInstructor() {
        return this.userType.equals("instructor");
    }

    public void registerCredentials(String userName, String userType, int sessionId) {
        this.userName = userName;
        this.userType = userType;
        this.sessionId = sessionId;
    }

    public SimUser(WebSocketSession ws) {
        this.wsAPI = ws;
    }

}
