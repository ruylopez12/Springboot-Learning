package com.example.Reactive.Config;

import com.example.Reactive.Rrquest.GreetingRequest;
import com.example.Reactive.Rrquest.GreetingResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.Map;

@Configuration
public class WebSocketConfig {

    private final GreetingsProducer greetingsProducer;

    public WebSocketConfig(GreetingsProducer greetingsProducer) {
        this.greetingsProducer = greetingsProducer;
    }

    @Bean
    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping(){
            @Override
            public void setUrlMap(Map<String, ?> urlMap) {
                super.setUrlMap(Map.of("/ws/greetings",wsh));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter(){
        return  new WebSocketHandlerAdapter();
    }

    @Bean
    WebSocketHandler webSocketHandler() {
        return session -> {
            // Create a Flux of WebSocket messages based on incoming messages
            Flux<WebSocketMessage> response = session
                    .receive() // Receive messages from the WebSocket session
                    .map(WebSocketMessage::getPayloadAsText) // Get payload as text
                    .map(GreetingRequest::new) // Map to GreetingRequest object
                    .flatMap(greetingsProducer::greet) // Call greetingsProducer to process the greeting
                    .map(GreetingResponse::getMessage) // Extract message from GreetingResponse
                    .map(session::textMessage); // Create a WebSocket text message from the response

            // Send the response to the WebSocket session
            return session.send(response);
        };
    }

}
