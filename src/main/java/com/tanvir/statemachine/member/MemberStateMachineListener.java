package com.tanvir.statemachine.member;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MemberStateMachineListener {

    private final StateMachineFactory<MemberStates, MemberEvents> stateMachine;

    public MemberStateMachineListener(@Qualifier("memberStateMachine") StateMachineFactory<MemberStates, MemberEvents> stateMachine) {
        this.stateMachine = stateMachine;
    }

   /* @PostConstruct
    public void checkStateMachineInitialization() {
        stateMachine.addStateListener(new StateMachineListenerAdapter<MemberStates, MemberEvents>() {
            @Override
            public void stateChanged(State<MemberStates, MemberEvents> from, State<MemberStates, MemberEvents> to) {
                log.info("State changed from {} to {}", from, to);
            }
        });
        stateMachine.startReactively()
            .subscribe();
    }*/
}
