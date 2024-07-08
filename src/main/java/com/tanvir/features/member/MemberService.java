package com.tanvir.features.member;

import com.tanvir.statemachine.member.MemberEvents;
import com.tanvir.statemachine.member.MemberStates;
import com.tanvir.statemachine.member.StateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final StateMachine<MemberStates, MemberEvents> stateMachine;
    private final MemberRepository memberRepository;
    private final StateChangeInterceptor stateChangeInterceptor;

    public Mono<Member> createMember(Member member) {
        member.setWorkflowStatus(MemberStates.PENDING_APPROVAL);
        member.setStatus(MemberStates.INACTIVE);
        return memberRepository.save(member);

    }

    public void approveMember(String memberId) {
        memberRepository.findById(memberId)
            .map(member -> {
                member.setWorkflowStatus(MemberStates.APPROVED);
                return member;
            })
            .flatMap(memberRepository::save)
            .subscribe();
    }

    private Mono<String> stateChange(String id, MemberEvents event) {
        return memberRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Member not found")))
            .flatMap(member -> {
                MemberStates currentState = member.getWorkflowStatus();
                return buildStateMachine(id, currentState)
                    .flatMap(stateMachine -> {
                        return stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build()))
                            .then(Mono.just(stateMachine.getState().getId().toString()));
                    });
            });
    }

    private Mono<StateMachine<MemberStates, MemberEvents>> buildStateMachine(String appId, MemberStates state) {
        StateMachine<MemberStates, MemberEvents> memberStateMachine = stateMachine;
        return memberStateMachine.stopReactively() // Stop the state machine reactively
            .then(Mono.defer(() -> Mono.fromRunnable(() -> // Use Mono.fromRunnable to ensure reactive execution
                memberStateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(stateChangeInterceptor);
                    sma.resetStateMachineReactively(new DefaultStateMachineContext<>(state, null, null, null)).subscribe();
                })
            )))
            .then(memberStateMachine.startReactively()) // Start the state machine reactively
            .thenReturn(memberStateMachine); // Return the state machine wrapped in a Mono
    }


}
