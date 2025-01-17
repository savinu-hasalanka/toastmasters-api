package com.example.app.service.user;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;

import com.example.app.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//@RequiredArgsConstructor
public class UserService {
//    private final UserRepository userRepository;

//    @Override
//    public AppUser addUser(UserRequest request) throws AlreadyExistsException {
//        return Optional.of(request).filter(u -> !userRepository.existsByUsername(u.getUsername()))
//                .map( req -> {
//                    var user = createUser(request);
//                    return userRepository.save(user);
//                })
//                .orElseThrow(() -> new AlreadyExistsException(request.getUsername() + " already exists"));
//
//    }
//
//    private AppUser createUser(UserRequest request) {
//        return new AppUser(
//                request.getUsername(),
//                request.getName(),
//                request.getPassword()
//        );
//    }
//
//    @Override
//    public AppUser updateUserByUsername(UserRequest user, String username) throws ResourceNotFoundException {
//        return Optional.ofNullable(getUserByUsername(username))
//                .map(oldUser -> {
//                    oldUser.setUsername(user.getUsername());
//                    oldUser.setName(user.getName());
//                    oldUser.setPassword(user.getPassword());
//                    return userRepository.save(oldUser);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
//    }
//
//    @Override
//    public AppUser getUserByUsername(String username) throws ResourceNotFoundException {
//        return userRepository.findByUsername(username)
//                        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
//    }
//
////    @Override
////    public void deleteUserByUsername(String username) throws ResourceNotFoundException {
////        userRepository.findByUsername(username)
////                .ifPresentOrElse(userRepository::delete, () -> {
////                    throw new ResourceNotFoundException("User not found!");
////                });
////    }
//
//    @Override
//    public void deleteUserByUsername(String username) throws ResourceNotFoundException {
//        Optional<AppUser> user = userRepository.findByUsername(username);
//        if (user.isPresent()) {
//            userRepository.delete(user.get());
//        } else {
//            throw new ResourceNotFoundException("User not found!");
//        }
//    }

}
