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
        try {
            String token = userService.login(user);
            return ResponseEntity.ok(new ApiResponse("Login success", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(new ApiResponse("Username and password do not match!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }
}
