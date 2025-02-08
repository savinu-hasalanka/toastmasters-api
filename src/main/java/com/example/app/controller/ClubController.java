package com.example.app.controller;

import com.example.app.dto.ClubDto;
import com.example.app.request.ClubLoginRequest;
import com.example.app.request.ClubRegisterRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.club.IClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/clubs")
public class ClubController {

    private final IClubService clubService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody ClubRegisterRequest request) {
        try {
            ClubDto clubDto = clubService.register(request);
            return ResponseEntity.ok(new ApiResponse("Club account created", clubDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody ClubLoginRequest club) {
        try {
            String token = clubService.login(club);
            return ResponseEntity.ok(new ApiResponse("Login success", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(new ApiResponse("ClubId and password do not match!", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }
}
