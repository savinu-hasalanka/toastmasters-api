package com.example.app.repo;

import com.example.app.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club getClubByClubId(Integer clubId);
}
