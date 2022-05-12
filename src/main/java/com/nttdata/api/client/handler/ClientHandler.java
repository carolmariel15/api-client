package com.nttdata.api.client.handler;

import com.nttdata.api.client.consumer.KafkaJsonConsumer;
import com.nttdata.api.client.document.Client;
import com.nttdata.api.client.producer.KafkaProducer;
import com.nttdata.api.client.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ClientHandler {

    private final ClientRepository clientRepository;

    private final KafkaJsonConsumer kafkaJsonConsumer;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    @Cacheable(value="userCache")
    public Mono<ServerResponse> getAllClients(ServerRequest serverRequest) {
        return  ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(clientRepository.findAll().log(), Client.class);
    }

    public Mono<ServerResponse> getClient(ServerRequest serverRequest) {
        var id = String.valueOf(serverRequest.pathVariable("id"));
        var client = clientRepository.findById(id);
        return client.flatMap(i -> ServerResponse.ok()
                    .contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(client, Client.class))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        var clientMono = serverRequest.bodyToMono(Client.class);
        return  clientMono.flatMap(c -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(clientRepository.save(c), Client.class));
    }

    public Mono<ServerResponse> edit(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Client.class).flatMap(v -> {
            return clientRepository.findById(v.getId()).flatMap(c -> {
                c.setName(v.getName());
                c.setSurname(v.getSurname());
                c.setImei(v.getImei());
                c.setPhone(v.getPhone());
                c.setEmail(v.getEmail());
                return ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(clientRepository.save(c), Client.class);
            }).switchIfEmpty(notFound);
        });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = String.valueOf(serverRequest.pathVariable("id"));
        return clientRepository.findById(id)
                .flatMap(c -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(clientRepository.delete(c), Void.class))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> getConsumer(ServerRequest serverRequest) {
        return kafkaJsonConsumer.getCurrency()
                .flatMap(c -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(c, Object.class))
                .switchIfEmpty(notFound);
    }

}
