package edu.usc.marshall.centralis22.handler;

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
    private final UserPersistenceService user;

    /**
     * Reads in the message. Parses it according to the API,
     *  validates credentials, and processes the request.
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // logger must be in method-scope to be thread safe.
        Logger logger = LoggerFactory.getLogger(WebSocketAPIHandler.class);
        logger.trace(session.getId() + ": " + message);

        RequestResponseEntity rre = new RequestResponseEntity();

        try {
            Map<String, Object> data = JacksonUtil.toMap(message.getPayload());
            int respondId = (int)data.get("request_id");
            rre.csetRespondId(respondId);
            dispatcher.dispatch(data, rre);
        }
        catch(Exception e)
        {
            logger.warn(e.getMessage());
            // TODO: send status_response
            session.sendMessage(new TextMessage("U fked up!"));
        }

        TextMessage msg = new TextMessage(rre.toString());
        String test = rre.toString();
        System.out.println(test);
        session.sendMessage(msg);
    }

    public WebSocketAPIHandler(
            RequestDispatcher dispatcher,
            UserPersistenceService user) {
        this.dispatcher = dispatcher;
        this.user = user;
    }
}
