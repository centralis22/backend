package edu.usc.marshall.centralis22.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.usc.marshall.centralis22.security.UserPersistenceService;
import edu.usc.marshall.centralis22.service.RequestDispatcher;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import edu.usc.marshall.centralis22.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

@Component
public class WebSocketAPIHandler extends TextWebSocketHandler {

    private final RequestDispatcher dispatcher;

    /**
     * Receives a message, parses it into a map according to the API,
     * and forwards the content to a handler.
     *
     * <p>The handler handles server-client I/O. In addition, it checks
     * JSON parsing errors, and ensures that a response can be made.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // logger must be in method-scope to be thread safe.
        Logger logger = LoggerFactory.getLogger(WebSocketAPIHandler.class);
        logger.trace(session.getId() + ": " + message);

        // RequestResponseEntity passed-by-reference to concrete handlers.
        RequestResponseEntity rre = new RequestResponseEntity();

        try {
            Map<String, Object> data = JacksonUtil.toMap(message.getPayload());
            int respondId = (int)data.get("request_id");
            rre.setRespondId(respondId);
            dispatcher.dispatch(data, rre);
        }
        catch(JsonProcessingException jpe) {
            logger.warn("JPE: " + jpe.getMessage());
            rre
                    .setStatusCode(400)
                    .setContent("JSON processing error.");
        }
        catch(Exception e) {
            logger.error("E: " + e.getMessage());
            e.printStackTrace(System.err);
            rre
                    .setStatusCode(500)
                    .setContent("Internal server error.");
        }

        TextMessage msg = new TextMessage(rre.toString());
        session.sendMessage(msg);
    }

    public WebSocketAPIHandler(
            RequestDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
