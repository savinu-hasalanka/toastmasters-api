package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Meeting;
import com.example.app.model.types.Role;
import com.example.app.request.AddRolePlayerRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.meetingRoleplayer.MeetingRolePlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meeting-role-player")
public class MeetingRolePlayerController {
    private final MeetingRolePlayerService meetingRolePlayerService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRolePlayer(@RequestBody AddRolePlayerRequest request) {
        try {
            Long meetingId = request.getMeetingId();
            Role role = Role.valueOf(request.getRole());
            String rolePlayerUsername = request.getRolePlayerUsername();
            meetingRolePlayerService.addRolePlayer(meetingId, role, rolePlayerUsername);
            return ResponseEntity.ok(new ApiResponse("Role-player added", request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse(e.getMessage(), null));
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

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteRolePlayer(
            @RequestParam Long meetingId,
            @RequestParam String roleName
    ) {
        try {
            Role role = Role.valueOf(roleName);
            meetingRolePlayerService.removeRolePlayer(meetingId, role);
            return ResponseEntity.ok(new ApiResponse("Role-player removed", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(NOT_ACCEPTABLE)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/role-players/{meetingId}")
    public ResponseEntity<ApiResponse> getRolePlayers(@PathVariable Long meetingId) {
        try {
            Map<Role, String> map = meetingRolePlayerService.getRolePlayers(meetingId);
            return ResponseEntity.ok(new ApiResponse("Success!", map));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/meeting/{rolePlayerUsername}")
    public ResponseEntity<ApiResponse> getMeetings(@PathVariable String rolePlayerUsername) {
        try {
            Set<Meeting> meetings = meetingRolePlayerService.getMeetings(rolePlayerUsername);
            return ResponseEntity.ok(new ApiResponse("Success!", meetings));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
