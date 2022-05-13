package com.nttdata.api.client.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class KafkaJsonConsumerImpl implements KafkaJsonConsumer {

    private static Mono<Object> currency = null;
    private static Flux<Object> typeOperation = null;

    @KafkaListener(topics = "topic-currency", groupId = "myGroup")
    public void consumeCurrency(Object c) {
        currency = Mono.just(c);
        System.out.println(currency);
    }

    @Override
    public Mono<Object> getCurrency() {
        System.out.println("Entra currency");

        return currency;
    }

}
