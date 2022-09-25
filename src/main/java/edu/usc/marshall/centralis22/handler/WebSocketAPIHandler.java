package edu.usc.marshall.centralis22.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.MapType;
import edu.usc.marshall.centralis22.util.JSONToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

public class WebSocketAPIHandler extends TextWebSocketHandler {

    /**
     * Reads in the message. Parses it according to the API,
     *  validates credentials, and processes the request.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println(message.getPayload());

        try {
            Map<String, Object> data = JSONToMap.toMap(message.getPayload());
            for (var ea : data.keySet()) {
                System.out.println(ea);
                System.out.println(data.get(ea));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!" + session.getId());
        session.sendMessage(msg);
    }
}
