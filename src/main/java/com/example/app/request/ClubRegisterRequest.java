package com.example.app.request;

import lombok.Data;

@Data
public class ClubRegisterRequest {
    private String name;
    private String email;
    private String password;
}
