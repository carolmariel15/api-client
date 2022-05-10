package com.nttdata.api.client.router;

import com.nttdata.api.client.handler.ClientHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static  org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ClientRouter {

    @Bean
    public RouterFunction<ServerResponse> clientRouterFunc(ClientHandler clientHandler) {
        return RouterFunctions.route(GET("/client").and(accept(MediaType.TEXT_EVENT_STREAM)), clientHandler::getAllClients)
                .andRoute(GET("/client/{id}").and(accept(MediaType.TEXT_EVENT_STREAM)), clientHandler::getClient)
                .andRoute(POST("/client").and(accept(MediaType.TEXT_EVENT_STREAM)), clientHandler::create)
                .andRoute(PUT("/client").and(accept(MediaType.TEXT_EVENT_STREAM)), clientHandler::edit)
                .andRoute(DELETE("/client/{id}").and(accept(MediaType.TEXT_EVENT_STREAM)), clientHandler::delete);
    }

}
