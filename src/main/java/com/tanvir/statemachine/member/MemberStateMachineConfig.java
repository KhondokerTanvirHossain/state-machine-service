package com.tanvir.statemachine.member;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "memberStateMachine")
public class MemberStateMachineConfig extends EnumStateMachineConfigurerAdapter<MemberStates, MemberEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<MemberStates, MemberEvents> states) throws Exception {
        states
            .withStates()
            .initial(MemberStates.INACTIVE)
            .state(MemberStates.PENDING_APPROVAL)
            .state(MemberStates.REVIEWED)
            .state(MemberStates.APPROVED)
            .state(MemberStates.REJECTED)
            .end(MemberStates.ACTIVE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MemberStates, MemberEvents> transitions) throws Exception {
        transitions
            .withExternal()
            .source(MemberStates.INACTIVE).target(MemberStates.PENDING_APPROVAL).event(MemberEvents.CREATE)
            .and().withExternal()
            .source(MemberStates.PENDING_APPROVAL).target(MemberStates.REVIEWED).event(MemberEvents.REVIEW)
            .and().withExternal()
            .source(MemberStates.REVIEWED).target(MemberStates.APPROVED).event(MemberEvents.APPROVE)
            .and().withExternal()
            .source(MemberStates.REVIEWED).target(MemberStates.REJECTED).event(MemberEvents.REJECT)
            .and().withExternal()
            .source(MemberStates.APPROVED).target(MemberStates.ACTIVE).event(MemberEvents.APPROVE);
    }

    /*@Bean
    public StateMachineListener<MemberStates, MemberEvents> listener() {
        return new StateMachineListenerAdapter<MemberStates, MemberEvents>() {
            @Override
            public void stateChanged(State<MemberStates, MemberEvents> from, State<MemberStates, MemberEvents> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }*/
}
