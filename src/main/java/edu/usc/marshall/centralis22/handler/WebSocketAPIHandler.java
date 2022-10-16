package edu.usc.marshall.centralis22.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.security.UserPersistenceService;
import edu.usc.marshall.centralis22.service.RequestDispatcher;
import edu.usc.marshall.centralis22.util.JacksonUtil;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

@Component
public class WebSocketAPIHandler extends TextWebSocketHandler {

    private UserPersistenceService ups;
    private RequestDispatcher dispatcher;

    /**
     * The handler mimics the functionality of a {@code RestController}
     * It receives a message, parses the message into a map, and forwards
     * the content to a {@code RequestHandler}.
     *
     * <p>The handler is responsible for server-client I/O. In addition,
     * it checks JSON parsing errors, and ensures that a response is returned.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // logger must be in method-scope to be thread safe.
        Logger logger = LoggerFactory.getLogger(WebSocketAPIHandler.class);
        logger.trace(session.getId() + ": " + message);

        // RequestResponseEntity passed-by-reference to concrete handlers.
        RequestResponseEntity rre = new RequestResponseEntity();

        try {
            SimUser user = ups.getUser(session);
            Map<String, Object> data = JacksonUtil.toMap(message.getPayload());
            int respondId = (int)data.get("request_id");
            rre.setRespondId(respondId);
            dispatcher.dispatch(user, data, rre);
        }
        catch(JsonProcessingException jpe) {
            logger.warn("JPE Message: " + message.getPayload());
            rre
                    .setStatusCode(400)
                    .setContent("JSON processing error.");
        }
        catch(Exception e) {
            logger.error("E Message: " + message.getPayload());
            e.printStackTrace(System.err);
            rre
                    .setStatusCode(500)
                    .setContent("Internal server error.");
        }

        TextMessage msg = new TextMessage(rre.toString());
        session.sendMessage(msg);
    }

    /**
     * A {@link SimUser} should be added whenever a new connection is made.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        ups.addUser(session);
    }

    /**
     * The corresponding {@link SimUser} should be removed when its connection
     * is closed.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        ups.removeUser(session);
    }

    @Autowired
    public void setDispatcher(RequestDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Autowired
    public void setUps(UserPersistenceService ups) {
        this.ups = ups;
    }
}
