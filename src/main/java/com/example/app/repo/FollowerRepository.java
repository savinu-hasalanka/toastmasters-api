package com.example.app.repo;

import com.example.app.model.Club;
import com.example.app.model.Follower;
import com.example.app.model.composite_keys.UserClubCompositePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, UserClubCompositePK> {
    List<Follower> findAllByClub(Club club);
}
