package com.example.app.model.composite_keys;

import com.example.app.model.types.Role;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRolePlayerId {
    private Long meetingId;
    @Enumerated(EnumType.STRING)
    private Role role;
}
