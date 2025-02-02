package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Exco;
import com.example.app.model.types.ExcoRole;
import com.example.app.request.AddExcoRequest;
import com.example.app.request.DeleteExcoRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.exco.IExcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/exco")
public class ExcoController {
    private final IExcoService excoService;

    @GetMapping("/get/{clubId}")
    public ResponseEntity<ApiResponse> getExcoMembers(@PathVariable Integer clubId) {
        try {
            List<Exco> excoMembers = excoService.getAllExcoMembers(clubId);
            return ResponseEntity.ok(new ApiResponse("Exco list", excoMembers));
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
    public ResponseEntity<ApiResponse> addExcoMember(@RequestBody AddExcoRequest addExcoRequest) {
        try {
            ExcoRole.valueOf(addExcoRequest.getRole());
            addExcoRequest.setClubId(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));
            excoService.addExcoMember(addExcoRequest);
            return ResponseEntity.ok(new ApiResponse("Added", null));
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity
                    .status(BAD_REQUEST)
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
            e.printStackTrace();
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateExcoMember() {
        return null;
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeExcoMember(@RequestBody DeleteExcoRequest deleteExcoRequest) {
        try {
            deleteExcoRequest.setClubId(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
            excoService.deleteExcoMember(deleteExcoRequest);
            return ResponseEntity.ok(new ApiResponse("Removed", null));
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
