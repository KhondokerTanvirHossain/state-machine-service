package com.tanvir.features.memberv2.unauthmember;

import com.tanvir.statemachine.unauthmember.UnauthMemberStates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "unauthorized_members")
public class UnauthMember {
    @Id
    private String id;
    private String otherAttribute;
    private UnauthMemberStates status; // Active, Inactive, Closed, Deleted

    // Getters and Setters
}
