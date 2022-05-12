package com.nttdata.api.client.producer;

import com.nttdata.api.client.document.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Client> kafkaTemplate;

    public KafkaProducer(@Qualifier("kafkaJsonTemplate") KafkaTemplate<String, Client> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClient(Client c) {
        LOGGER.info("Enviando cliente", c);
        this.kafkaTemplate.send("topic-client", c);
    }

}
