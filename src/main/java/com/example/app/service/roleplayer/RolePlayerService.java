package com.example.app.service.roleplayer;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Meeting;
import com.example.app.model.RolePlayer;
import com.example.app.model.RolePlayerRequest;
import com.example.app.model.composite_keys.UserMeetingRoleCompositePK;
import com.example.app.model.types.MeetingRole;
import com.example.app.repo.AppUserRepository;
import com.example.app.repo.MeetingRepository;
import com.example.app.repo.RolePlayerRepository;
import com.example.app.repo.RolePlayerRequestRepository;
import com.example.app.request.RolePlayerRequestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePlayerService implements IRolePlayerService {

    private final RolePlayerRepository rolePlayerRepository;
    private final RolePlayerRequestRepository rolePlayerRequestRepository;
    private final MeetingRepository meetingRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<RolePlayer> getRolePlayers(Integer meetingId) throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        return rolePlayerRepository.findAllByMeeting(meeting);
    }

    @Override
    @Transactional
    public void addRolePlayer(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException, AlreadyExistsException {
        Integer meetingId = rolePlayerRequestRequest.getMeetingId();
        String username = rolePlayerRequestRequest.getRolePlayerUsername();
        MeetingRole role = MeetingRole.valueOf(rolePlayerRequestRequest.getRole());

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (rolePlayerRepository.existsByUserMeetingRoleCompositePK_MeetingIdAndUserMeetingRoleCompositePK_Role(meetingId, role)) {
            throw new AlreadyExistsException(role + " already taken");
        }

        UserMeetingRoleCompositePK primaryKey = new UserMeetingRoleCompositePK(username, meetingId, role);
        RolePlayer rolePlayer = new RolePlayer(primaryKey, user, meeting, null);
        rolePlayerRepository.save(rolePlayer);
    }

    @Override
    @Transactional
    public void removeRolePlayer(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException {
        Integer meetingId = rolePlayerRequestRequest.getMeetingId();
        String username = rolePlayerRequestRequest.getRolePlayerUsername();
        MeetingRole role = MeetingRole.valueOf(rolePlayerRequestRequest.getRole());

        meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        UserMeetingRoleCompositePK primaryKey = new UserMeetingRoleCompositePK(username, meetingId, role);

        if (!(rolePlayerRepository.existsById(primaryKey)))
            throw new ResourceNotFoundException("Role-player not found");

        rolePlayerRepository.deleteById(primaryKey);
    }

    @Override
    public List<RolePlayerRequest> getRolePlayerRequests(Integer meetingId) throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        return rolePlayerRequestRepository.findAllByMeeting(meeting);
    }

    @Override
    @Transactional
    public void makeRolePlayerRequest(RolePlayerRequestRequest request) throws ResourceNotFoundException, AlreadyExistsException {
        Integer meetingId = request.getMeetingId();
        String username = request.getRolePlayerUsername();
        MeetingRole role = MeetingRole.valueOf(request.getRole());

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        UserMeetingRoleCompositePK primaryKey = new UserMeetingRoleCompositePK(username, meetingId, role);

        if (rolePlayerRequestRepository.existsById(primaryKey))
            throw new AlreadyExistsException("Request already exist");

        RolePlayerRequest rolePlayerRequest = new RolePlayerRequest(primaryKey, user, meeting);
        rolePlayerRequestRepository.save(rolePlayerRequest);
    }

    @Override
    @Transactional
    public void removeRolePlayerRequest(RolePlayerRequestRequest request) throws ResourceNotFoundException {
        Integer meetingId = request.getMeetingId();
        String username = request.getRolePlayerUsername();
        MeetingRole role = MeetingRole.valueOf(request.getRole());

        meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        UserMeetingRoleCompositePK primaryKey = new UserMeetingRoleCompositePK(username, meetingId, role);

        if (!(rolePlayerRequestRepository.existsById(primaryKey)))
            throw new ResourceNotFoundException("Request not found");

        rolePlayerRequestRepository.deleteById(primaryKey);
    }

    @Override
    @Transactional
    public void acceptRolePlayerRequest(RolePlayerRequestRequest request) throws ResourceNotFoundException, AlreadyExistsException {
        Integer meetingId = request.getMeetingId();
        String username = request.getRolePlayerUsername();
        MeetingRole role = MeetingRole.valueOf(request.getRole());

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));

        AppUser user = appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        UserMeetingRoleCompositePK primaryKey = new UserMeetingRoleCompositePK(username, meetingId, role);

        if (!(rolePlayerRequestRepository.existsById(primaryKey)))
            throw new ResourceNotFoundException("Request not found");

        List<MeetingRole> duplicateRoles = List.of(MeetingRole.SPEAKER, MeetingRole.SPEAKER_TT, MeetingRole.EVALUATOR_SPEECHES, MeetingRole.EVALUATOR_TT);

        if (!duplicateRoles.contains(role)) {
            if (rolePlayerRepository.existsByUserMeetingRoleCompositePK_MeetingIdAndUserMeetingRoleCompositePK_Role(meetingId, role)) {
                throw new AlreadyExistsException(role + " already taken");
            }
        }

        rolePlayerRequestRepository.deleteById(primaryKey);

        RolePlayer rolePlayer = new RolePlayer(primaryKey, user, meeting, null);
        rolePlayerRepository.save(rolePlayer);
    }
}
