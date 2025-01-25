package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.request.UserLoginRequest;
import com.example.app.request.UserRegisterRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.user.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/users")
public class AppUserController {
    private final IAppUserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterRequest user) {
        System.out.println("Inside register method : AppUserController.register");
        try {
            userService.register(user);
            return ResponseEntity.ok(new ApiResponse("User account created", user));
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginRequest user) {
        System.out.println("Hit api login");
        try {
            String token = userService.login(user);
            return ResponseEntity.ok(new ApiResponse("Login success", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(new ApiResponse("Username and password do not match!", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }
//
//    @PutMapping("/user/{username}/update")
//    public ResponseEntity<ApiResponse> updateUser(
//            @RequestBody UserRequest user,
//            @PathVariable String username) {
//
//        try {
//            if (!(user.getUsername().equals(username)))
//                throw new IllegalArgumentException("url username and request username must be the same.");
//            userService.updateUserByUsername(user, username);
//            return ResponseEntity.ok(new ApiResponse("User updated", user));
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity
//                    .status(NOT_FOUND)
//                    .body(new ApiResponse(e.getMessage(), null));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity
//                    .status(CONFLICT)
//                    .body(new ApiResponse("Error", e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse("Error", e.getMessage()));
//        }
//    }
//
//    @GetMapping("/user/{username}/user")
//    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
//        try {
//            AppUser user = userService.getUserByUsername(username);
//            UserDto userDto = createUserDto(user);
//            return ResponseEntity.ok(new ApiResponse("User found", userDto));
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity
//                    .status(NOT_FOUND)
//                    .body(new ApiResponse(e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse("Error", e.getMessage()));
//        }
//    }
//
//    private UserDto createUserDto(AppUser user) {
//        return new UserDto(user.getUsername(), user.getName());
//    }
//
//    @DeleteMapping("/user/{username}/delete")
//    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String username) {
//        try {
//            userService.deleteUserByUsername(username);
//            return ResponseEntity.ok(new ApiResponse("User deleted", username));
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity
//                    .status(NOT_FOUND)
//                    .body(new ApiResponse(e.getMessage(), null));
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponse("Error", e.getMessage()));
//        }
//    }
}
