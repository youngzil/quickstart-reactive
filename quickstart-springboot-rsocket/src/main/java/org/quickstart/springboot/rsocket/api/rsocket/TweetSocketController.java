package org.quickstart.springboot.rsocket.api.rsocket;

import org.quickstart.springboot.rsocket.domain.Tweet;
import org.quickstart.springboot.rsocket.domain.TweetRequest;
import org.quickstart.springboot.rsocket.service.TweetService;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TweetSocketController {

    private final TweetService service;

    public TweetSocketController(TweetService service) {
        this.service = service;
    }

    @MessageMapping("tweets.by.author")
    public Flux<Tweet> getByAuthor(TweetRequest request) {
        return service.getByAuthor(request.getAuthor());
    }

}
