package com.example.app.service.user;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.model.AppUser;
import com.example.app.request.UserRequest;

public interface IAppUserService {
    AppUser register(UserRequest user) throws AlreadyExistsException;
//    AppUser updateUserByUsername(UserRequest user, String username) throws ResourceNotFoundException;
//    AppUser getUserByUsername(String username) throws ResourceNotFoundException;
//    void deleteUserByUsername(String username) throws ResourceNotFoundException;
}
