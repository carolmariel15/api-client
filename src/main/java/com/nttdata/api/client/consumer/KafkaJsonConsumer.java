package com.nttdata.api.client.consumer;

import reactor.core.publisher.Mono;

public interface KafkaJsonConsumer {

    public Mono<Object> getCurrency();

}
