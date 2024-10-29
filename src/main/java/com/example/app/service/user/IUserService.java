package com.example.app.service.user;

import com.example.app.model.AppUser;
import com.example.app.request.UserRequest;

public interface IUserService {
    AppUser addUser(UserRequest user);
    AppUser updateUserByUsername(UserRequest user, String username);
    AppUser getUserByUsername(String username);
    void deleteUserByUsername(String username);
}
