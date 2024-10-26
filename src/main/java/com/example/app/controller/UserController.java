package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.request.AddUserRequest;
import com.example.app.request.UserUpdateRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@RequestBody AddUserRequest user) {
        try {
            userService.addUser(user);
            return ResponseEntity.ok(new ApiResponse("User added", user));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PutMapping("/user/{username}/update")
    public ResponseEntity<ApiResponse> updateUser(
            @RequestBody UserUpdateRequest user,
            @PathVariable String username) {

        try {
            userService.updateUserByUsername(user, username);
            return ResponseEntity.ok(new ApiResponse("User updated", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{username}/user")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        try {
            AppUser user = userService.getUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse("User found", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/user/{username}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok(new ApiResponse("User deleted", username));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
