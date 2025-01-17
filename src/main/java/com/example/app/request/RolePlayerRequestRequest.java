package com.example.app.request;

import lombok.Getter;

@Getter
public class RolePlayerRequestRequest {
    private Integer meetingId;
    private String role;
    private String rolePlayerUsername;
}
