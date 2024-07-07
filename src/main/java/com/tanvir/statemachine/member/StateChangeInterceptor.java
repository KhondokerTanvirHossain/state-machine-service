package com.tanvir.statemachine.member;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
public class StateChangeInterceptor extends StateMachineInterceptorAdapter<MemberStates, MemberEvents> {

    @Override
    public void preStateChange(State<MemberStates, MemberEvents> state, Message<MemberEvents> message, Transition<MemberStates, MemberEvents> transition, StateMachine<MemberStates, MemberEvents> stateMachine, StateMachine<MemberStates, MemberEvents> rootStateMachine) {
        // Implementation
    }
}
