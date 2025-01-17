package com.example.app.repo;

import com.example.app.model.Club;
import com.example.app.model.Exco;
import com.example.app.model.composite_keys.UserClubCompositePK;
import com.example.app.model.types.ExcoRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExcoRepository extends JpaRepository<Exco, UserClubCompositePK> {
    List<Exco> findAllByClub(Club club);

    boolean existsByUserClubCompositePK(UserClubCompositePK userClubCompositePK);

    boolean existsByRoleAndClub(ExcoRole role, Club club);
}
