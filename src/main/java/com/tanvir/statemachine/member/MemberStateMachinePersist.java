package com.tanvir.statemachine.member;

import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

public class MemberStateMachinePersist implements StateMachinePersist<MemberStates, MemberEvents, String> {
    @Override
    public void write(StateMachineContext<MemberStates, MemberEvents> stateMachineContext, String s) throws Exception {

    }

    @Override
    public StateMachineContext<MemberStates, MemberEvents> read(String s) throws Exception {
        return null;
    }

    // Implement methods to persist state machine state
    // This could involve storing the state in the database associated with the memberId
}
