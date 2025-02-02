package com.example.app.service.club;

import com.example.app.dto.ClubDto;
import com.example.app.model.Club;
import com.example.app.repo.ClubRepository;
import com.example.app.request.ClubLoginRequest;
import com.example.app.request.ClubRegisterRequest;
import com.example.app.service.jwt.JWTService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClubService implements IClubService {

    private final ClubRepository clubRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClubService(
            ClubRepository clubRepository,
            @Qualifier("clubAuthenticationManager") AuthenticationManager authenticationManager,
            JWTService jwtService,
            BCryptPasswordEncoder passwordEncoder) {
        this.clubRepository = clubRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClubDto register(ClubRegisterRequest request) {
        Club club = createClub(request);
        return createClubDto(clubRepository.save(club));
    }

    @Override
    public String login(ClubLoginRequest club) {
        System.out.println("Inside login method");
        System.out.println("Club id: " + club.getClubId());
        System.out.println("Club id: " + Integer.parseInt(club.getClubId()));
        System.out.println("Club password: " + club.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(Integer.parseInt(club.getClubId()), club.getPassword())
        );

        System.out.println("Authentication object created");

        if (authentication.isAuthenticated()) {
            System.out.println("Authenticated");
        return jwtService.generateToken(String.valueOf(club.getClubId()), "club", List.of("ROLE_CLUB"));
        }

        System.out.println("Authentication Failed");
        return null;
    }

    private ClubDto createClubDto(Club club) {
        return new ClubDto(club.getClubId(), club.getName(), club.getMeetings());
    }

    private Club createClub(ClubRegisterRequest request) {
        return new Club(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
    }

}
