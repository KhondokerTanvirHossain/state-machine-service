package com.tanvir.features.member;

import com.tanvir.statemachine.member.MemberEvents;
import com.tanvir.statemachine.member.MemberStates;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final StateMachineFactory<MemberStates, MemberEvents> stateMachineFactory;

    public void createMember(Member member) {
        // Logic to create an unauthorized member
        // Trigger state transition
    }

    public void approveMember(String memberId) {
        // Logic to approve member
        // Trigger state transition
    }

    // Other service methods
}
