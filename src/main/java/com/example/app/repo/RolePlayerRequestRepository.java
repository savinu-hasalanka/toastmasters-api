package com.example.app.repo;

import com.example.app.model.Meeting;
import com.example.app.model.RolePlayerRequest;
import com.example.app.model.composite_keys.UserMeetingRoleCompositePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePlayerRequestRepository extends JpaRepository<RolePlayerRequest, UserMeetingRoleCompositePK> {
    List<RolePlayerRequest> findAllByMeeting(Meeting meeting);
}
