package com.example.app.service.meetingRoleplayer;

import com.example.app.model.Meeting;
import com.example.app.model.types.Role;

import java.util.Map;
import java.util.Set;

public interface IMeetingRolePlayerService {
    void addRolePlayer(Long meetingId, Role role, String rolePlayerUsername);
    void removeRolePlayer(Long meetingId, Role role);
    Map<Role, String> getRolePlayers(Long meetingId);
    Set<Meeting> getMeetings(String rolePlayerUsername);
}
