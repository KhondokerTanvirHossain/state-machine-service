package com.tanvir.statemachine.member;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<MemberStates, MemberEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<MemberStates, MemberEvents> states) throws Exception {
        states.withStates()
            .initial(MemberStates.UNAUTHORIZED)
            .state(MemberStates.PENDING_APPROVAL)
            .state(MemberStates.APPROVED)
            .end(MemberStates.DELETED)
            .states(EnumSet.allOf(MemberStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MemberStates, MemberEvents> transitions) throws Exception {
        transitions
            .withExternal().source(MemberStates.UNAUTHORIZED).target(MemberStates.PENDING_APPROVAL).event(MemberEvents.CREATE)
            .and()
            .withExternal().source(MemberStates.PENDING_APPROVAL).target(MemberStates.APPROVED).event(MemberEvents.APPROVE)
            .and()
            .withExternal().source(MemberStates.APPROVED).target(MemberStates.ACTIVE).event(MemberEvents.ACTIVATE)
        // Define other transitions
        ;
    }
}
