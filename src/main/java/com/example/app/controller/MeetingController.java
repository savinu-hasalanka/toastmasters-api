package com.example.app.controller;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Meeting;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.meeting.IMeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meetings")
public class MeetingController {
    private final IMeetingService meetingService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMeeting(@RequestBody AddMeetingRequest meeting) {
        try {
            meetingService.addMeeting(meeting);
            return ResponseEntity.ok(new ApiResponse("Meeting added", meeting));
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

    @PutMapping("/meeting/{meetingId}/update")
    public ResponseEntity<ApiResponse> updateMeeting(
            @PathVariable Long meetingId,
            @RequestBody UpdateMeetingRequest meeting) {

        try {
            meetingService.updateMeeting(meeting, meetingId);
            return ResponseEntity.ok(new ApiResponse("Meeting updated", meeting));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("meeting/{host}/meeting")
    public ResponseEntity<ApiResponse> getMeetingByHost (@PathVariable String host) {
        try {
            List<Meeting> meetings = meetingService.getMeetingByHost(host);
            return ResponseEntity.ok(new ApiResponse("Meetings fetched", meetings));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }
}
