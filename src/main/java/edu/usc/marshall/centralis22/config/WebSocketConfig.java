package edu.usc.marshall.centralis22.config;

import edu.usc.marshall.centralis22.handler.WebSocketAPIHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Basic configurations for WebSocket, including url mappings,
 * and security settings.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketAPIHandler webSocketAPIHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(webSocketAPIHandler, "/api")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Autowired
    public WebSocketConfig(WebSocketAPIHandler webSocketAPIHandler) {
        this.webSocketAPIHandler = webSocketAPIHandler;
    }
}