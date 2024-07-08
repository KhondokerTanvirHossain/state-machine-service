package com.tanvir.statemachine.member;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "memberStateMachine")
public class MemberStateMachineConfig extends EnumStateMachineConfigurerAdapter<MemberStates, MemberEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<MemberStates, MemberEvents> states) throws Exception {
        states
            .withStates()
            .initial(MemberStates.NEW)
            .states(EnumSet.allOf(MemberStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MemberStates, MemberEvents> transitions) throws Exception {
        transitions
            .withExternal()
            .source(MemberStates.NEW).target(MemberStates.PENDING_APPROVAL).event(MemberEvents.SUBMIT)
            .and().withExternal()
            .source(MemberStates.PENDING_APPROVAL).target(MemberStates.REVIEWED).event(MemberEvents.REVIEW)
            .and().withExternal()
            .source(MemberStates.PENDING_APPROVAL).target(MemberStates.REVIEWED).event(MemberEvents.REJECT)
            .and().withExternal()
            .source(MemberStates.REVIEWED).target(MemberStates.APPROVED).event(MemberEvents.APPROVE)
            .and().withExternal()
            .source(MemberStates.REVIEWED).target(MemberStates.REJECTED).event(MemberEvents.REJECT)
            .and().withExternal()
            .source(MemberStates.INACTIVE).target(MemberStates.ACTIVE).event(MemberEvents.ACTIVATE)
            .and().withExternal()
            .source(MemberStates.ACTIVE).target(MemberStates.INACTIVE).event(MemberEvents.DEACTIVATE)
            .and().withExternal()
            .source(MemberStates.INACTIVE).target(MemberStates.CLOSED).event(MemberEvents.CLOSE)
        ;
    }

}
