package com.example.app.request;

import lombok.Data;

@Data
public class ClubLoginRequest {
    private String clubId;
    private String password;
}
