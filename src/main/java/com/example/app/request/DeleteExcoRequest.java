package com.example.app.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DeleteExcoRequest {
    private String username;
    @Setter
    private int clubId;
}
