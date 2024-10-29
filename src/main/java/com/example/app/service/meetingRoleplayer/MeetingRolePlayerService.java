package com.example.app.service.meetingRoleplayer;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Meeting;
import com.example.app.model.MeetingRolePlayer;
import com.example.app.model.composite_keys.MeetingRolePlayerId;
import com.example.app.model.types.Role;
import com.example.app.repo.MeetingRepository;
import com.example.app.repo.MeetingRolePlayerRepository;
import com.example.app.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MeetingRolePlayerService implements IMeetingRolePlayerService {
    private final MeetingRolePlayerRepository meetingRolePlayerRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public void addRolePlayer(Long meetingId, Role role, String rolePlayerUsername) {
        Meeting meeting = meetingRepository
                .findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        AppUser rolePlayer = userRepository
                .findByUsername(rolePlayerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MeetingRolePlayerId meetingRolePlayerId = new MeetingRolePlayerId(meetingId, role);

        if ((meetingRolePlayerRepository.existsById(meetingRolePlayerId)))
            throw new AlreadyExistsException("Meeting role player already exists");

        meetingRolePlayerRepository.save(
                new MeetingRolePlayer(
                        meetingRolePlayerId,
                        rolePlayer,
                        meeting,
                        role
                )
        );
    }

    @Override
    public void removeRolePlayer(Long meetingId, Role role) {
        meetingRolePlayerRepository.findById(new MeetingRolePlayerId(meetingId, role))
                .ifPresentOrElse(
                        meetingRolePlayerRepository::delete,
                        () -> {throw new ResourceNotFoundException("meeting-role not found");}
                );
    }

    @Override
    public List<MeetingRolePlayer> getRolePlayers(Long meetingId) {
        return meetingRolePlayerRepository.findAllByMeetingId(meetingId);
    }

    @Override
    public Set<Meeting> getMeetings(String rolePlayerUsername) {
        Set<Meeting> meetings = new HashSet<>();
        AppUser rolePlayer = userRepository.findByUsername(rolePlayerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Role-player not found"));
        List<MeetingRolePlayer> meetingRolePlayers = meetingRolePlayerRepository.findAllByRolePlayer(rolePlayer);


        for (MeetingRolePlayer meetingRolePlayer : meetingRolePlayers) {
            meetings.add(meetingRolePlayer.getMeeting());
        }

        return meetings;
    }
}
