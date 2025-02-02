package com.example.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddExcoRequest {
    private String username;
    @Setter
    private Integer clubId;
    private String role;
}
