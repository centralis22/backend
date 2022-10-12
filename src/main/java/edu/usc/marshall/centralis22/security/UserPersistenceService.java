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

import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@code UserPersistenceService} maps each {@code WebSocketSession},
 * using its unique session ID, to a {@link SimUser}. A user may connect
 * to the server through multiple sessions, e.g. by opening multiple
 * browser tabs, but each session will have only one user.
 */
@Service
public class UserPersistenceService {

    /**
     * Maps each {@code sessionId} to a {@link SimUser}.
     */
    private final ConcurrentHashMap<String, SimUser> sessionMap =
            new ConcurrentHashMap<>();

    /**
     * Adds a placeholder {@link SimUser} when a new {@code WebSocketSession}
     * is opened.
     *
     * @param sessionId {@code WebSocketSession} ID.
     */
    public void addUser(String sessionId) {
        sessionMap.putIfAbsent(sessionId, new SimUser());
    }

    /**
     * Retrieves the {@link SimUser} of a {@code WebSocketSession}.
     *
     * @param sessionId {@code WebSocketSession} ID.
     * @return {@link SimUser}
     */
    public SimUser getUser(String sessionId) {
        return sessionMap.get(sessionId);
    }

    /**
     * Removes the  map entry when a {@code WebSocketSession} has been closed.
     *
     * @param sessionId {@code WebSocketSession} ID.
     */
    public void removeUser(String sessionId) {
        sessionMap.remove(sessionId);
    }
}
