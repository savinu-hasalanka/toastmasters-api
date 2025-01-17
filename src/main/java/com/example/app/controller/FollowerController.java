package com.example.app.controller;

import com.example.app.dto.UserDto;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.response.ApiResponse;
import com.example.app.service.follower.IFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/followers")
public class FollowerController {
    private final IFollowerService followerService;

    @GetMapping("/get/{clubId}")
    public ResponseEntity<ApiResponse> getFollowers(@PathVariable Integer clubId) {
        try {
            List<UserDto> followers = followerService.getFollowers(clubId);
            return ResponseEntity.ok(new ApiResponse("Followers found", followers));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PostMapping("/follow/{clubId}")
    public ResponseEntity<ApiResponse> followClub(@PathVariable Integer clubId) {
        // TODO retrieve username from the authenticate object
        try {
            followerService.follow(clubId, "bbrown");
            return ResponseEntity.ok(new ApiResponse("Followed", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
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

    @DeleteMapping("/unfollow/{clubId}")
    public ResponseEntity<ApiResponse> unfollowClub(@PathVariable Integer clubId) {
        // TODO retrieve username from the authenticate object
        try {
            followerService.unfollow(clubId, "bbrown");
            return ResponseEntity.ok(new ApiResponse("Unfollowed", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }
}
