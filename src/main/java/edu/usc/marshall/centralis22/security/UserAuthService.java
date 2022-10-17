package edu.usc.marshall.centralis22.security;

import edu.usc.marshall.centralis22.model.Instructor;
import edu.usc.marshall.centralis22.model.SimSession;
import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.model.Team;
import edu.usc.marshall.centralis22.repository.InstructorRepository;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final Logger logger = LoggerFactory.getLogger(UserAuthService.class);

    private final SessionRepository sessr;
    private final InstructorRepository instr;
    private final TeamRepository teamr;
    private final UserPersistenceService ups;

    /**
     * Authenticates a {@link SimUser}.
     *
     * <p>For teams, only their session and username is required. A new team with
     * the given username will be created if one is not found.
     *
     * @param user
     * @param userType frontend: admin/student.
     * @param userName
     * @param userPswd
     * @param sessionId Simulation session ID. A server-generated 6-digit number.
     * @return 200, on auth success. 400, on non-existent session or userType.
     *  403, on failed instructor credentials.
     */
    public int authenticateUser(
            SimUser user,
            String userType,
            String userName,
            String userPswd,
            int sessionId) {

        // Error finding session
        SimSession simSession = sessr.findBySeid(sessionId);
        if(simSession == null) {
            logger.debug("Session: " + sessionId + " not found");
            return 400;
        }

        // Frontend API uses "admin", backend uses "instructor"
        if(userType.equals("admin")) {
            return authenticateInstructor(user, userName, userPswd, sessionId);
        }

        // Frontend API uses "student", backend uses "team"
        if(userType.equals("student")) {
            Team team = teamr.findBySeidAndTeamName(sessionId, userName);
            if(team == null) {
                team = new Team(42, sessionId, userName);
                teamr.save(team);
            }
            // Always success
            user.registerCredentials(userName, "team", sessionId);
            ups.addUserToSession(user, sessionId);
            logger.debug("Student 200");
            return 200;
        }

        return 400;
    }

    /**
     * Authenticates an {@link Instructor}.
     *
     * @param user
     * @param userName
     * @param userPswd
     * @param sessionId Simulation session ID. A server-generated 6-digit number.
     * @return 200, on auth success. 403, on failed instructor credentials.
     */
    public int authenticateInstructor(
            SimUser user,
            String userName,
            String userPswd,
            int sessionId) {
        Instructor instructor = instr.findByUserName(userName);
        // Username password error
        if(instructor == null
                || !instructor.getPassword().equals(userPswd)) {
            logger.debug("Instructor 403");
            return 403;
        }
        // Success
        user.registerCredentials(userName, "instructor", sessionId);
        ups.addUserToSession(user, sessionId);
        logger.debug("Instructor 200");
        return 200;
    }

    @Autowired
    public UserAuthService(
            SessionRepository sessr,
            InstructorRepository instr,
            TeamRepository teamr,
            UserPersistenceService ups) {
        this.sessr = sessr;
        this.instr = instr;
        this.teamr = teamr;
        this.ups = ups;
    }
}
