package com.example.app.request;

import lombok.Getter;

@Getter
public class AddRolePlayerRequest {
    private Long meetingId;
    private String role;
    private String rolePlayerUsername;
}
