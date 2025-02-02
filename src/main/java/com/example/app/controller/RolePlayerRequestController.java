package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.RolePlayer;
import com.example.app.model.RolePlayerRequest;
import com.example.app.request.RolePlayerRequestRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.roleplayer.IRolePlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/role-players")
public class RolePlayerRequestController {

    private final IRolePlayerService rolePlayerService;

    @GetMapping("/get/{meetingId}")
    public ResponseEntity<ApiResponse> getRolePlayers(@PathVariable Integer meetingId) {
        try {
            List<RolePlayer> rolePlayers = rolePlayerService.getRolePlayers(meetingId);
            return ResponseEntity.ok(new ApiResponse("Role-players found", rolePlayers));
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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addRolePlayer(@RequestBody RolePlayerRequestRequest request) {
        // TODO: Check if MeetingRole is valid
        try {
            rolePlayerService.addRolePlayer(request);
            return ResponseEntity.ok(new ApiResponse("Role-player added", null));
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

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeRolePlayer(@RequestBody RolePlayerRequestRequest request) {
        try {
            rolePlayerService.removeRolePlayer(request);
            return ResponseEntity.ok(new ApiResponse("Role-player removed", null));
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

    @GetMapping("/requests/get/{meetingId}")
    public ResponseEntity<ApiResponse> getRequests(@PathVariable Integer meetingId) {
        try {
            List<RolePlayerRequest> rolePlayerRequests = rolePlayerService.getRolePlayerRequests(meetingId);
            return ResponseEntity.ok(new ApiResponse("Role-player requests found", rolePlayerRequests));
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

    @PostMapping("/requests/request")
    public ResponseEntity<ApiResponse> request(@RequestBody RolePlayerRequestRequest request) {
        try {
            rolePlayerService.makeRolePlayerRequest(request);
            return ResponseEntity.ok(new ApiResponse("Made role-player request.", null));
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

    @DeleteMapping("/requests/withdraw")
    public ResponseEntity<ApiResponse> withdraw(@RequestBody RolePlayerRequestRequest request) {
        try {
            rolePlayerService.removeRolePlayerRequest(request);
            return ResponseEntity.ok(new ApiResponse("Role-player request removed", null));
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

    @PostMapping("/requests/accept")
    public ResponseEntity<ApiResponse> accept(@RequestBody RolePlayerRequestRequest request) {
        try {
            rolePlayerService.acceptRolePlayerRequest(request);
            return ResponseEntity.ok(new ApiResponse("Role-player request accepted.", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
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

    @DeleteMapping("/requests/decline")
    public ResponseEntity<ApiResponse> decline(@RequestBody RolePlayerRequestRequest request) {
        return withdraw(request);
    }

}
