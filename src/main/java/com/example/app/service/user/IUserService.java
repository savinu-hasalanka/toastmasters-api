package com.example.app.service.user;

import com.example.app.model.AppUser;
import com.example.app.request.AddUserRequest;
import com.example.app.request.UserUpdateRequest;

public interface IUserService {
    AppUser addUser(AddUserRequest user);
    AppUser updateUserByUsername(UserUpdateRequest user, String username);
    AppUser getUserByUsername(String username);
    void deleteUserByUsername(String username);
}
