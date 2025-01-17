package com.example.app.repo;

import com.example.app.model.Meeting;
import com.example.app.model.RolePlayer;
import com.example.app.model.composite_keys.UserMeetingRoleCompositePK;
import com.example.app.model.types.MeetingRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePlayerRepository extends JpaRepository<RolePlayer, UserMeetingRoleCompositePK> {
    List<RolePlayer> findAllByMeeting(Meeting meeting);

    boolean existsByUserMeetingRoleCompositePK_MeetingIdAndUserMeetingRoleCompositePK_Role(Integer meetingId, MeetingRole meetingRole);
}
