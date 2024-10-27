package com.example.app.service.user;

import com.example.app.model.AppUser;
import com.example.app.request.AddUserRequest;
import com.example.app.request.UpdateUserRequest;

public interface IUserService {
    AppUser addUser(AddUserRequest user);
    AppUser updateUserByUsername(UpdateUserRequest user, String username);
    AppUser getUserByUsername(String username);
    void deleteUserByUsername(String username);
}
