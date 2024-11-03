package com.example.app.service.meetingRoleplayer;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Meeting;
import com.example.app.model.MeetingRolePlayer;
import com.example.app.model.types.Role;

import java.util.List;
import java.util.Set;

public interface IMeetingRolePlayerService {
    void addRolePlayer(Long meetingId, Role role, String rolePlayerUsername) throws AlreadyExistsException, ResourceNotFoundException;
    void removeRolePlayer(Long meetingId, Role role) throws ResourceNotFoundException;
    List<MeetingRolePlayer> getRolePlayers(Long meetingId);
    Set<Meeting> getMeetings(String rolePlayerUsername) throws ResourceNotFoundException;
}
