package com.tanvir.features.member;

import com.tanvir.statemachine.member.MemberStates;
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
@Document(collection = "members")
public class Member {
    @Id
    private String id;
    private String otherAttribute;
    private MemberStates workflowStatus;
    private String status; // Active, Inactive, Closed, Deleted

    // Getters and Setters
}
