package com.example.app.request;

import lombok.Data;

@Data
public class AddUserRequest {
    private String username;
    private String name;
    private String password;
}