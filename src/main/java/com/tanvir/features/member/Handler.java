package com.tanvir.features.member;

import com.tanvir.core.util.exception.ErrorHandler;
import com.tanvir.core.util.exception.ExceptionHandlerUtil;
import com.tanvir.features.turnstile.EventData;
import com.tanvir.statemachine.member.MemberEvents;
import com.tanvir.statemachine.member.MemberStates;
import com.tanvir.statemachine.turnstile.TurnstileEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final SimpleMemberService service;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UnauthorizedMember.class)
            .flatMap(service::createMember)
            .flatMap(member -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(member))
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
//            .onErrorResume(Predicate.not(ExceptionHandlerUtil.class::isInstance), e -> ErrorHandler.buildErrorResponseForUncaught(e, serverRequest));
    }

    public Mono<ServerResponse> review(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDto.class)
            .flatMap(requestDto -> service.reviewMember(requestDto.getId()))
            .flatMap(member -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(member))
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> approve(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDto.class)
            .flatMap(requestDto -> service.approveMember(requestDto.getId()))
                .flatMap(member -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(member))
                .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> reject(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDto.class)
            .flatMap(requestDto -> service.rejectMember(requestDto.getId()))
                .flatMap(member -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(member))
                .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDto.class)
            .flatMap(requestDto -> service.getById(requestDto.getId()))
                .flatMap(member -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(member))
                .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> getListOfAuthorizedMembers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getListOfAuthorizedMembers(), Member.class)
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> getListOfUnAuthorizedMembers(ServerRequest serverRequest) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getListOfUnAuthorizedMembers(), UnauthorizedMember.class)
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

}
