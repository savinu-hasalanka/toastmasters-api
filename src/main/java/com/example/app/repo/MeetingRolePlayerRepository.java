package com.example.app.repo;

import com.example.app.model.AppUser;
import com.example.app.model.MeetingRolePlayer;
import com.example.app.model.composite_keys.MeetingRolePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRolePlayerRepository extends JpaRepository<MeetingRolePlayer, MeetingRolePlayerId> {
    List<MeetingRolePlayer> findAllByMeetingId(Long meetingId);

    List<MeetingRolePlayer> findAllByRolePlayer(AppUser rolePlayer);
}
