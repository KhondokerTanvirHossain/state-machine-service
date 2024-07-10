package com.tanvir.features.memberv2;

import com.tanvir.core.util.CommonFunctions;
import com.tanvir.statemachine.unauthmember.UnauthMemberEvents;
import com.tanvir.statemachine.unauthmember.UnauthMemberStates;
import org.springframework.statemachine.StateMachineEventResult;

public class EventResult {
    private String id;
    private String event;
    private String state;
    private StateMachineEventResult.ResultType resultType;
    private final StateMachineEventResult<UnauthMemberStates, UnauthMemberEvents> result;

    public EventResult(StateMachineEventResult<UnauthMemberStates, UnauthMemberEvents> result) {
        this.result = result;
        this.id = result.getRegion().getId();
        this.event = result.getMessage().getPayload().name();
        this.state = result.getRegion().getState().getId().name();
        this.resultType = result.getResultType();
    }

    public String getId() { return id; }
    public String getEvent() {
        return event;
    }
    public String getState() { return state; }

    public StateMachineEventResult.ResultType getResultType() {
        return resultType;
    }

    public String toString() {
        return CommonFunctions.buildGsonBuilder(this);
    }
}
