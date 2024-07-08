package com.tanvir.features.member;

import com.tanvir.core.util.CommonFunctions;
import org.springframework.statemachine.StateMachineEventResult;

import java.util.Map;

public class EventResultResponseDto {
    private String regionId;
    private String regionUuid;
    private String messagePayload;
    private String event;
    private Map<String, Object> messageHeaders;
    private String regionState;
    private StateMachineEventResult.ResultType resultType;

    public String getRegionId() {
        return regionId;
    }

    public String getRegionUuid() {
        return regionUuid;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public String getEvent() {
        return event;
    }

    public Map<String, Object> getMessageHeaders() {
        return messageHeaders;
    }

    public String getRegionState() {
        return regionState;
    }

    public StateMachineEventResult.ResultType getResultType() {
        return resultType;
    }

    private final StateMachineEventResult<MemberStates, MemberEvents> result;

    public EventResultResponseDto(StateMachineEventResult<MemberStates, MemberEvents> result) {
        this.result = result;
        this.regionId = result.getRegion().getId();
        this.regionUuid = result.getRegion().getUuid().toString();
        this.messagePayload = result.getMessage().getPayload().name();
        this.event = result.getMessage().getPayload().name();
        this.messageHeaders = result.getMessage().getHeaders();
        this.regionState = result.getRegion().getState().getId().name();
        this.resultType = result.getResultType();
    }


    public String toString() {
        return CommonFunctions.buildGsonBuilder(this);
    }
}
