package com.example.app.service.user;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.repo.UserRepository;
import com.example.app.request.AddUserRequest;
import com.example.app.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public AppUser addUser(AddUserRequest request) {
        return Optional.of(request).filter(u -> !userRepository.existsByUsername(u.getUsername()))
                .map( req -> {
                    var user = createUser(request);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException(request.getUsername() + " already exists"));

    }

    private AppUser createUser(AddUserRequest request) {
        return new AppUser(
                request.getUsername(),
                request.getName(),
                request.getPassword()
        );
    }

    @Override
    public AppUser updateUserByUsername(UpdateUserRequest user, String username) {
        return Optional.ofNullable(getUserByUsername(username))
                .map(oldUser -> {
                    oldUser.setUsername(user.getUsername());
                    oldUser.setName(user.getName());
                    oldUser.setPassword(user.getPassword());
                    return userRepository.save(oldUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found!");
                });
    }
}
