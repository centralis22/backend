package edu.usc.marshall.centralis22.security;

import edu.usc.marshall.centralis22.model.Instructor;
import edu.usc.marshall.centralis22.model.Session;
import edu.usc.marshall.centralis22.model.Team;
import edu.usc.marshall.centralis22.repository.InstructorRepository;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.repository.TeamRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Temporary object to store user information.
 */
@Component
public class UserPersistenceService {

    private final InstructorRepository insr;
    private final TeamRepository teamr;
    private final SessionRepository sessr;

    private String userName = "unauthorized1";
    private String userType = "unauthorized";
    private int sessionId = 0;

    public int authenticateUser(
            String userType,
            String userName,
            String userPswd,
            int sessionId) {

        // Error finding session
        Session session = sessr.findBySeid(sessionId);
        if(session == null) {
            return 400;
        }

        if(userType.equals("instructor")) {
            Instructor instructor = insr.findByUserName(userName);
            // Error finding username
            if(instructor == null
                    || !userPswd.equals(instructor.getPassword())) {
                return 403;
            }
            // Success
            this.userName = userName;
            this.userType = "instructor";
            return 200;
        }

        // API mismatch, student == team
        if(userType.equals("student")) {
            Team team = teamr.findBySeidAndTeamName(sessionId, userName);
            if(team == null) {
                team = new Team(42, sessionId, userName);
                teamr.save(team);
            }
            // Always success
            this.userName = userName;
            this.userType = "team";
            return 200;
        }

        return 400;
    }

    public boolean isInstructor() {
        return userType.equals("instructor");
    }

    public boolean isStudent() {
        return userType.equals("team");
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public UserPersistenceService(
            InstructorRepository insr,
            TeamRepository teamr,
            SessionRepository sessr) {
        this.insr = insr;
        this.teamr = teamr;
        this.sessr = sessr;
    }
}
