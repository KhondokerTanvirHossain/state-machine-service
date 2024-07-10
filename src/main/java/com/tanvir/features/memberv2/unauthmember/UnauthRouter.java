package com.tanvir.features.memberv2.unauthmember;

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
public class UnauthRouter {

    private final UnauthHandler handler;

    @Bean
    public RouterFunction<ServerResponse> unauthMemberRouterConfig() {
        return RouterFunctions.route()
            .nest(RequestPredicates.accept(MediaType.APPLICATION_JSON), builder -> builder
                .POST(RouteNames.BASE_URL_V2.concat(RouteNames.MEMBER).concat(RouteNames.CREATE), handler::create)
                .POST(RouteNames.BASE_URL_V2.concat(RouteNames.MEMBER_WITH_ID).concat(RouteNames.EVENT_WITH_NAME), handler::changeState)
                .GET(RouteNames.BASE_URL_V2.concat(RouteNames.MEMBER_WITH_ID).concat(RouteNames.GET_BY_ID), handler::getById)
                .GET(RouteNames.BASE_URL_V2.concat(RouteNames.MEMBER).concat(RouteNames.GET_ALL_AUTHORIZED), handler::getListOfUnAuthorizedMembers))
            .build();
    }
}
