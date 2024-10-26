package com.example.app.repo;

import com.example.app.model.AppUser;
import com.example.app.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByHost(AppUser host);
}
