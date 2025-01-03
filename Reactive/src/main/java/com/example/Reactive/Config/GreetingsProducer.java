package com.example.Reactive.Config;

import com.example.Reactive.Rrquest.GreetingRequest;
import com.example.Reactive.Rrquest.GreetingResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Component
public class GreetingsProducer {

    Flux<GreetingResponse> greet(GreetingRequest greetingRequest) {
        return Flux.fromStream(Stream.generate(() -> new GreetingResponse("hello" + greetingRequest.getName() + ":" + Instant.now() + ";"))).
                delayElements(Duration.ofSeconds(1));
    }
}
