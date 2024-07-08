package com.tanvir.features.member;

import com.tanvir.core.routes.RouteNames;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class Router {
    private final Handler handler;

    @Bean
    public RouterFunction<ServerResponse> memberRouterConfig() {
        return RouterFunctions.route()
            .nest(RequestPredicates.accept(MediaType.APPLICATION_JSON), builder -> builder
                .POST(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.CREATE), handler::create)
                .POST(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.REVIEW), handler::review)
                .POST(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.APPROVE), handler::approve)
                .POST(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.REJECT), handler::reject)
                .GET(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.GET_BY_ID), handler::getById)
                .GET(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.GET_ALL_AUTHORIZED), handler::getListOfAuthorizedMembers)
                .GET(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.GET_ALL_UNAUTHORIZED), handler::getListOfUnAuthorizedMembers)
                .GET(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.STATE), handler::state)
                .POST(RouteNames.BASE_URL.concat(RouteNames.MEMBER).concat(RouteNames.EVENTS), handler::events))
            .build();
    }
}
