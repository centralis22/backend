package edu.usc.marshall.centralis22.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.MapType;
import edu.usc.marshall.centralis22.service.ActionDispatcher;
import edu.usc.marshall.centralis22.util.JSONToMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class WebSocketAPIHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketAPIHandler.class);

    /**
     * Reads in the message. Parses it according to the API,
     *  validates credentials, and processes the request.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.trace(session.getId() + ": " + message);

        try {
            Map<String, Object> data = JSONToMap.toMap(message.getPayload());

            // TODO: auth

            // TODO: handle action
        }
        catch(Exception e)
        {
            logger.warn("Unable to parse:" + e.getMessage());
            // TODO: sent status_response
        }

        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!" + session.getId());
        session.sendMessage(msg);
    }
}
