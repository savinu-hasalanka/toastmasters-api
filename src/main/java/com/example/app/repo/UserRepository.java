package com.example.app.repo;

import com.example.app.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {

    boolean existsByUsername(String username);
    Optional<AppUser> findByUsername(String username);

}
