package com.tanvir.statemachine.member;

import com.tanvir.statemachine.unauthmember.UnauthMemberEvents;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "memberStateMachineFactory")
public class MemberStateMachineConfig extends EnumStateMachineConfigurerAdapter<MemberStates, MemberEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<MemberStates, MemberEvents> states) throws Exception {
        states
            .withStates()
            .initial(MemberStates.ACTIVE)
            .states(EnumSet.allOf(MemberStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MemberStates, MemberEvents> transitions) throws Exception {
        transitions
            .withExternal()
            .source(MemberStates.ACTIVE).target(MemberStates.INACTIVE).event(MemberEvents.INACTIVATE)
            .and().withExternal()
            .source(MemberStates.INACTIVE).target(MemberStates.ACTIVE).event(MemberEvents.ACTIVATE)
            .and().withExternal()
            .source(MemberStates.INACTIVE).target(MemberStates.CLOSED).event(MemberEvents.CLOSE)
        ;
    }

}
