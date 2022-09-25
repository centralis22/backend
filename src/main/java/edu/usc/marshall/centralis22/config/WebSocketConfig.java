package edu.usc.marshall.centralis22.config;

import edu.usc.marshall.centralis22.handler.WebSocketAPIHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/*
 * Sets up a simple WebSocket.
 * https://stackoverflow.com/questions/27158106/websocket-with-sockjs-spring-4-but-without-stomp
 *
 * Enables CORS
 * Spring docs 25.2.6 WebSocket Support, Configuring allowed origins
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(wscWebSocketAPIHandler(), "/api")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Bean
    public WebSocketHandler wscWebSocketAPIHandler() {
        return new WebSocketAPIHandler();
    }
}