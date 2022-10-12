package edu.usc.marshall.centralis22.security;

import edu.usc.marshall.centralis22.model.Instructor;
import edu.usc.marshall.centralis22.model.SimSession;
import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.model.Team;
import edu.usc.marshall.centralis22.repository.InstructorRepository;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final SessionRepository sessr;
    private final InstructorRepository instr;
    private final TeamRepository teamr;

    /**
     * Authenticates {@link SimUser}.
     *
     * <p>All users must join a valid session. For instructors, their username
     * and password must match. For teams, only their username is required. A
     * new team with the given username will be created if one is not found.
     *
     * @param user
     * @param userType instructor/student.
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
            return 400;
        }

        if(userType.equals("instructor")) {
            Instructor instructor = instr.findByUserName(userName);
            // Username password error
            if(instructor == null
                    || !instructor.getPassword().equals(userPswd)) {
                return 403;
            }
            // Success
            user.setUserName(userName);
            user.setUserType("instructor");
            user.setSessionId(sessionId);
            return 200;
        }

        // Frontend API "student", backend "team"
        if(userType.equals("student")) {
            Team team = teamr.findBySeidAndTeamName(sessionId, userName);
            if(team == null) {
                team = new Team(42, sessionId, userName);
                teamr.save(team);
            }
            // Always success
            user.setUserName(userName);
            user.setUserType("team");
            user.setSessionId(sessionId);
            return 200;
        }

        return 400;
    }

    @Autowired
    public UserAuthService(
            SessionRepository sessr,
            InstructorRepository instr,
            TeamRepository teamr) {
        this.sessr = sessr;
        this.instr = instr;
        this.teamr = teamr;
    }
}