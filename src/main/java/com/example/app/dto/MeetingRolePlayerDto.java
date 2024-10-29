package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MeetingRolePlayerDto implements Serializable {
    private Long meetingId;
    private List<RolePlayerDto> rolePlayers;
}
