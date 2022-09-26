package edu.usc.marshall.centralis22.handler;

import edu.usc.marshall.centralis22.service.RequestDispatcher;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import edu.usc.marshall.centralis22.util.JSONToMap;
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
     * Reads in the message. Parses it according to the API,
     *  validates credentials, and processes the request.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // logger must be in method-scope to be thread safe.
        Logger logger = LoggerFactory.getLogger(WebSocketAPIHandler.class);
        logger.trace(session.getId() + ": " + message);

        RequestResponseEntity are = new RequestResponseEntity();

        try {
            Map<String, Object> data = JSONToMap.toMap(message.getPayload());

            // TODO: auth

            // TODO: handle action
            dispatcher.dispatch(
                    (String)data.get("request"),
                    data.get("content"),
                    are
            );
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage());
            // TODO: sent status_response
        }

        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!" + session.getId());
        session.sendMessage(msg);
    }

    public WebSocketAPIHandler(RequestDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
