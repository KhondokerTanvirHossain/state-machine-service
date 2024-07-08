package com.tanvir.statemachine.unauthmember;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "unauthMemberStateMachineFactory")
public class UnauthMemberStateMachineConfig extends EnumStateMachineConfigurerAdapter<UnauthMemberStates, UnauthMemberEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<UnauthMemberStates, UnauthMemberEvents> states) throws Exception {
        states
            .withStates()
            .initial(UnauthMemberStates.PENDING_APPROVAL)
            .states(EnumSet.allOf(UnauthMemberStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UnauthMemberStates, UnauthMemberEvents> transitions) throws Exception {
        transitions
            .withExternal()
            .source(UnauthMemberStates.PENDING_APPROVAL).target(UnauthMemberStates.APPROVED).event(UnauthMemberEvents.APPROVE)
            .and().withExternal()
            .source(UnauthMemberStates.PENDING_APPROVAL).target(UnauthMemberStates.REJECTED).event(UnauthMemberEvents.REJECT)
        ;
    }

}
