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

    private final StateMachine<MemberStates, MemberEvents> stateMachine;
    private final MemberRepository memberRepository;
    private final UnauthorizedMemberRepository unauthorizedMemberRepository;
    private final ModelMapper modelMapper;

    public StateMachineMemberService(@Qualifier("memberStateMachine") StateMachine<MemberStates, MemberEvents> stateMachine,
                                     MemberRepository memberRepository,
                                     UnauthorizedMemberRepository unauthorizedMemberRepository,
                                     ModelMapper modelMapper) {
        this.stateMachine = stateMachine;
        this.memberRepository = memberRepository;
        this.unauthorizedMemberRepository = unauthorizedMemberRepository;
        this.modelMapper = modelMapper;
    }

    private Mono<StateMachine<MemberStates, MemberEvents>> buildStateMachine(String memberId) {
        StateMachine<MemberStates, MemberEvents> memberStateMachine = stateMachine;
        return Mono.fromRunnable(memberStateMachine::stopReactively)
            .then(Mono.fromRunnable(() ->
                memberStateMachine.getStateMachineAccessor()
                    .doWithAllRegions(access ->
                        access.resetStateMachineReactively(new DefaultStateMachineContext<>(MemberStates.INACTIVE, null, null, null)))
            ))
            .then(Mono.fromRunnable(memberStateMachine::startReactively))
            .thenReturn(memberStateMachine);
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
