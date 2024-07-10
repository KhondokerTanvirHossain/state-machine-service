package com.tanvir.features.memberv2.unauthmember;

import com.tanvir.features.memberv2.EventResult;
import com.tanvir.statemachine.unauthmember.UnauthMemberEvents;
import com.tanvir.statemachine.unauthmember.UnauthMemberStates;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UnauthMemberStateMachineService {

    private final StateMachineFactory<UnauthMemberStates, UnauthMemberEvents> stateMachineFactory;
    private final UnauthMemberRepository repository;

    public Mono<StateMachine<UnauthMemberStates, UnauthMemberEvents>> rehydrateStateMachine(String unauthMemberId) {
        return repository.findById(unauthMemberId)
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid member ID")))
            .flatMap(member -> {
                StateMachine<UnauthMemberStates, UnauthMemberEvents> stateMachine = stateMachineFactory.getStateMachine(unauthMemberId);
                return stateMachine.stopReactively().then(Mono.fromRunnable(() ->
                        stateMachine.getStateMachineAccessor().doWithAllRegions(access ->
                            access.resetStateMachineReactively(new DefaultStateMachineContext<>(member.getStatus(), null, null, null))
                                .subscribe()
                        ))
                    .then(stateMachine.startReactively()).thenReturn(stateMachine)
                );
            });
    }

    public Flux<EventResult> sendEvent(String memberId, UnauthMemberEvents event) {
        return rehydrateStateMachine(memberId)
            .flatMapMany(stateMachine -> stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build())))
            .map(EventResult::new)
            ;
    }
}
