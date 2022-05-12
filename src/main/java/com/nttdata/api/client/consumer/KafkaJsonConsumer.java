package com.nttdata.api.client.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class KafkaJsonConsumer implements  KafkaJsonConsumerImpl {

    private static Mono<Object> currency = null;
    private static Flux<Object> typeOperation = null;

    @KafkaListener(topics = "topic-currency", groupId = "myGroup")
    public void consumeCurrency(Object c) {
        currency = Mono.just(c);
        System.out.println(currency);
    }

    @KafkaListener(topics = "topic-typeoperation", groupId = "myGroup")
    public void consumeTypeOperation(Object typeOperation) {
        typeOperation = Flux.just(typeOperation);
    }

    @Override
    public Mono<Object> getCurrency() {
        System.out.println("Entra currency");

        return currency;
    }

    @Override
    public Flux<Object> getTypeOperation() {
        return typeOperation;
    }
}
