package edu.usc.marshall.centralis22.security;

import edu.usc.marshall.centralis22.model.SimUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import edu.usc.marshall.centralis22.handler.WebSocketAPIHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code UserPersistenceService} manages network users that are connected to
 * the WebSocket "/api" endpoint.
 *
 * <p>For each {@link WebSocketSession} connected to "/api", a new {@link SimUser}
 * should be created. A user may connect to the server through multiple
 * sessions, e.g. by opening multiple browser tabs, but each session will
 * have only one user.
 */
@Service
public class UserPersistenceService {

    private final Logger logger = LoggerFactory.getLogger(UserPersistenceService.class);

    /**
     * Maps each {@link WebSocketSession} ID to a {@link SimUser}.
     */
    private final ConcurrentHashMap<String, SimUser> wsMap =
            new ConcurrentHashMap<>();

    /**
     * Maps each {@code SimSession} to all of its {@link SimUser}.
     */
    private final ConcurrentHashMap<Integer, List<SimUser>> sessionMap =
            new ConcurrentHashMap<>();

    /**
     * Adds a {@link SimUser}.
     *
     * @param wsAPI {@link WebSocketSession} connected to the "/api" endpoint.
     */
    public void addUser(WebSocketSession wsAPI) {
        SimUser user = new SimUser(wsAPI);
        wsMap.putIfAbsent(wsAPI.getId(), user);
    }

    /**
     * Retrieves a {@link SimUser}.
     *
     * @param wsAPI {@link WebSocketSession} connected to the "/api" endpoint.
     * @return {@link SimUser}
     */
    public SimUser getUser(WebSocketSession wsAPI) {
        return wsMap.get(wsAPI.getId());
    }

    /**
     * Removes a {@link SimUser}.
     *
     * @param wsAPI {@link WebSocketSession} connected to the "/api" endpoint.
     */
    public void removeUser(WebSocketSession wsAPI) {
        SimUser user = wsMap.remove(wsAPI.getId());
        List<SimUser> sessionUsers = sessionMap.get(user.getSessionId());
        if(sessionUsers != null) {
            sessionUsers.remove(user);
        }
    }

    /**
     * Adds a {@link SimUser} to a {@code SimSession}.
     *
     * @param user
     * @param sessionId {@code SimSession} sessionID.
     */
    public void addUserToSession(SimUser user, int sessionId) {
        if(sessionMap.containsKey(sessionId)) {
            sessionMap.get(sessionId).add(user);
            logger.debug("Session " + sessionId + " adds user " + user.getUserName());
        }
        else {
            sessionMap.put(sessionId, new ArrayList<>(List.of(user)));
            logger.debug("Session " + sessionId + " adds initial user " + user.getUserName());
        }
    }

    /**
     * Retrieves all {@link SimUser} in a {@code SimSession}.
     *
     * @param sessionId {@code SimSession} sessionID.
     * @return List of {@link SimUser}.
     */
    public List<SimUser> getAllUsersInSession(int sessionId) {
        return sessionMap.get(sessionId);
    }
}
