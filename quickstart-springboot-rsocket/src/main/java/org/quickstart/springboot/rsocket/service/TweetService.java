package org.quickstart.springboot.rsocket.service;

import org.quickstart.springboot.rsocket.domain.Tweet;
import org.quickstart.springboot.rsocket.repository.TweetRepository;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TweetService {

    private TweetRepository repository;

    public TweetService(TweetRepository repository) {
        this.repository = repository;
    }

    public Flux<Tweet> getByAuthor(String author) {
        return Flux
                .interval(Duration.ZERO, Duration.ofSeconds(5))
                .flatMap(i -> repository.getByAuthor(author));       
    }

}
