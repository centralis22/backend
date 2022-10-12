package edu.usc.marshall.centralis22.model;

/**
 * A {@code SimUser} is any user, {@link Instructor} or {@link Team},
 * that is a part of a {@link SimSession}.
 *
 * <p>A team is associated with one session, while an instructor may freely
 * join any session. Although, an instructor is associated with at most one
 * session at a time, where it is then considered a {@code SimUser}. An
 * instructor has the option to not join any session when creating a new
 * one.
 */
public class SimUser {
    private String userName;
    private String userType;
    private int sessionId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isInstructor() {
        return this.userType.equals("instructor");
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public SimUser() {
        this.userName = "unauthorized1";
        this.userType = "unauthorized1";
        this.sessionId = -1;
    }

}
