package com.tanvir.features.turnstile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanvir.core.util.CommonFunctions;
import com.tanvir.statemachine.turnstile.TurnstileEvents;
import com.tanvir.statemachine.turnstile.TurnstileStates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.StateMachineEventResult.ResultType;


public class EventResult {
    
    private final StateMachineEventResult<TurnstileStates, TurnstileEvents> result;

    EventResult(StateMachineEventResult<TurnstileStates, TurnstileEvents> result) {
        this.result = result;
    }

    public ResultType getResultType() {
        return result.getResultType();
    }

    public TurnstileEvents getEvent() {
        return result.getMessage().getPayload();
    }

    @Override
    public String toString() {
        return CommonFunctions.buildGsonBuilder(this);
    }
}
