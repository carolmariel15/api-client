package com.nttdata.api.client.handler;

import com.nttdata.api.client.document.Client;
import com.nttdata.api.client.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@AllArgsConstructor
public class ClientHandler {

    private final ClientRepository clientRepository;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getAllClients(ServerRequest serverRequest) {
        return  ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(clientRepository.findAll().log(), Client.class);
    }

    public Mono<ServerResponse> getClient(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
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
                c.setCellPhoneNumber(v.getCellPhoneNumber());
                c.setEmail(v.getEmail());
                c.setCardNumber(v.getCardNumber());
                return ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(clientRepository.save(c), Client.class);
            }).switchIfEmpty(notFound);
        });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        return clientRepository.findById(id)
                .flatMap(c -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(clientRepository.delete(c), Void.class))
                .switchIfEmpty(notFound);
    }

}
