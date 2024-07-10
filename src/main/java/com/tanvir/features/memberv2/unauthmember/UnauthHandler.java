package com.tanvir.features.memberv2.unauthmember;

import com.tanvir.core.util.exception.ErrorHandler;
import com.tanvir.core.util.exception.ExceptionHandlerUtil;
import com.tanvir.features.memberv2.EventResult;
import com.tanvir.features.memberv2.RequestDto;
import com.tanvir.statemachine.unauthmember.UnauthMemberEvents;
import com.tanvir.statemachine.unauthmember.UnauthMemberStates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
@Slf4j
public class UnauthHandler {

    private final UnauthMemberStateMachineService service;
    private final UnauthMemberRepository repository;

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UnauthMember.class)
            .flatMap(unauthMember -> {
                unauthMember.setStatus(UnauthMemberStates.PENDING_APPROVAL);
                return repository.save(unauthMember);
            })
            .flatMap(member -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(member))
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
//            .onErrorResume(Predicate.not(ExceptionHandlerUtil.class::isInstance), e -> ErrorHandler.buildErrorResponseForUncaught(e, serverRequest));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestDto.class)
            .flatMap(requestDto -> repository.findById(requestDto.getId()))
                .flatMap(member -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(member))
                .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> getListOfUnAuthorizedMembers(ServerRequest serverRequest) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(repository.findAll(), UnauthMember.class)
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }

    public Mono<ServerResponse> changeState(ServerRequest serverRequest) {
        String memberId = serverRequest.pathVariable("memberId");
        UnauthMemberEvents event = UnauthMemberEvents.valueOf(serverRequest.pathVariable("eventName"));
        Flux<EventResult> eventResultFlux =  service.sendEvent(memberId, event)
            .publishOn(Schedulers.immediate())
            .doOnNext(eventResult -> {
                log.info("Event result: {}", eventResult);
                if (eventResult.getResultType().name().equals("ACCEPTED")) {
                    repository.findById(memberId)
                        .map(member -> {
                            member.setStatus(UnauthMemberStates.valueOf(eventResult.getState()));
                            return member;
                        })
                        .flatMap(repository::save)
                        .subscribe();
                }
            });
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventResultFlux, EventResult.class)
            .onErrorResume(ExceptionHandlerUtil.class, e -> ErrorHandler.buildErrorResponseForBusiness(e, serverRequest))
            ;
    }
}
