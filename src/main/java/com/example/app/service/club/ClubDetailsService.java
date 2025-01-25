package com.example.app.service.club;

import com.example.app.model.Club;
import com.example.app.repo.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClubDetailsService implements UserDetailsService {

    private final ClubRepository clubRepository;

    @Override
    public UserDetails loadUserByUsername(String clubID) throws UsernameNotFoundException {
        Club club = clubRepository.findById(Integer.valueOf(clubID))
                .orElseThrow(() -> new UsernameNotFoundException(clubID));

        return new ClubPrincipal(club);
    }

    public String getUserName() {
        return "";
    }


}
