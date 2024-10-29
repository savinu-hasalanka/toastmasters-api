package com.example.app.dto;

import com.example.app.model.types.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class RolePlayerDto implements Serializable {
    private Role role;
    private String rolePlayerUsername;
}
