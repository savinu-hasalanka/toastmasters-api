package com.example.app.model.composite_keys;

import jakarta.persistence.Embeddable;

@Embeddable
public class MeetingRolePlayerId {
    private Long meetingId;
    private String username;
}
