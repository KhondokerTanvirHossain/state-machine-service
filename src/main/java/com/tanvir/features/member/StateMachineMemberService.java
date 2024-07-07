package com.tanvir.features.member;

import com.tanvir.statemachine.member.MemberEvents;
import com.tanvir.statemachine.member.MemberStates;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StateMachineMemberService {

    private final StateMachineFactory<MemberStates, MemberEvents> stateMachineFactory;
    private final MemberRepository memberRepository;
    private final UnauthorizedMemberRepository unauthorizedMemberRepository;
    private final ModelMapper modelMapper;

    public StateMachineMemberService(@Qualifier("memberStateMachine") StateMachineFactory<MemberStates, MemberEvents> stateMachineFactory,
                                     MemberRepository memberRepository,
                                     UnauthorizedMemberRepository unauthorizedMemberRepository,
                                     ModelMapper modelMapper) {
        this.stateMachineFactory = stateMachineFactory;
        this.memberRepository = memberRepository;
        this.unauthorizedMemberRepository = unauthorizedMemberRepository;
        this.modelMapper = modelMapper;
    }

    private Mono<StateMachine<MemberStates, MemberEvents>> buildStateMachine(String memberId) {
        StateMachine<MemberStates, MemberEvents> stateMachine = stateMachineFactory.getStateMachine();
        return Mono.fromRunnable(stateMachine::stopReactively)
            .then(Mono.fromRunnable(() ->
                stateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access ->
                        access.resetStateMachineReactively(new DefaultStateMachineContext<>(MemberStates.INACTIVE, null, null, null)))
            ))
            .then(Mono.fromRunnable(stateMachine::startReactively))
            .thenReturn(stateMachine);
    }

    public Mono<UnauthorizedMember> createMember(UnauthorizedMember member) {
        return buildStateMachine(member.getId())
            .flatMap(stateMachine -> {
                Message<MemberEvents> createEvent = MessageBuilder.withPayload(MemberEvents.CREATE).build();
                return stateMachine.sendEvent(Mono.just(createEvent))
                    .then(Mono.fromCallable(() -> {
                        member.setWorkflowStatus(stateMachine.getState().getId());
                        member.setStatus(MemberStates.INACTIVE);
                        return member;
                    }));
            })
            .flatMap(unauthorizedMemberRepository::save);
    }

    // Similar modifications for reviewMember, rejectMember, and approveMember methods
    // Use stateMachine.sendEvent(...) and update member states based on the state machine's state

    // getById, getListOfAuthorizedMembers, getListOfUnAuthorizedMembers remain unchanged
}
