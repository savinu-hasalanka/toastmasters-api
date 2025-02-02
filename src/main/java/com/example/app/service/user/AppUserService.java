package com.example.app.service.user;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.model.AppUser;
import com.example.app.repo.AppUserRepository;
import com.example.app.request.UserLoginRequest;
import com.example.app.request.UserRegisterRequest;
import com.example.app.service.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {
    private final AppUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public AppUser register(UserRegisterRequest request) throws AlreadyExistsException {
        System.out.println("Inside register method : AppUserService.register");

        return Optional.of(request).filter(u -> !userRepository.existsByUsername(u.getUsername()))
                .map( req -> {
                    var user = createUser(request);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException(request.getUsername() + " already exists"));

    }

    private AppUser createUser(UserRegisterRequest request) {
        return new AppUser(
                request.getUsername(),
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
    }

    @Override
    public String login(UserLoginRequest user) {
        System.out.println("Inside login method");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        System.out.println("Authentication object created");

        if (authentication.isAuthenticated()) {
            System.out.println("Authenticated");
            return jwtService.generateToken(user.getUsername(), "user", List.of("ROLE_USER"));
        }

        System.out.println("Authentication Failed");
        return null;
    }
}
