package com.example.app.repo;

import com.example.app.model.Club;
import com.example.app.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    List<Meeting> findAllByClubOrVisitingClub(Club club, Club visitingClub);

}
