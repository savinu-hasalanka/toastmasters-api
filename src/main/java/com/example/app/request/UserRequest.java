package com.example.app.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String name;
    private String email;
    private String password;
}