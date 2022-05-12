package com.nttdata.api.client.consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface KafkaJsonConsumerImpl {

    public Mono<Object> getCurrency();
    public Flux<Object> getTypeOperation();

}
