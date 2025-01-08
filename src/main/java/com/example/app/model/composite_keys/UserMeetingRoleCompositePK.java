package com.example.app.model.composite_keys;

import com.example.app.model.types.MeetingRole;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class UserMeetingRoleCompositePK {

    private String username;
    private int meetingId;
    @Enumerated(EnumType.STRING)
    private MeetingRole role;

}
