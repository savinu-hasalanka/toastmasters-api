package com.example.app.controller;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Club;
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
@RequestMapping("api/v2/meetings")
public class MeetingController {
    private final IMeetingService meetingService;

    @GetMapping("/get/by-meeting/{meetingId}")
    public ResponseEntity<ApiResponse> getMeetingById(@PathVariable Integer meetingId) {
        try {
            Meeting meeting = meetingService.getMeetingById(meetingId);
            return ResponseEntity.ok(new ApiResponse("Meeting found", meeting));
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

    @GetMapping("/get/by-club/{clubId}")
    public ResponseEntity<ApiResponse> getMeetingsByClubId(@PathVariable Integer clubId) {
        try {
            List<Meeting> meetings = meetingService.getMeetingsByClubId(clubId);
            return ResponseEntity.ok(new ApiResponse("Meeting found", meetings));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addMeeting(@RequestBody AddMeetingRequest meeting) {
        // TODO get club information using authentication object
        meeting.setClub(new Club(1, "Alpha Toastmasters", "alpha@toastmasters.com", "alpha123"));

        try {
            meetingService.addMeeting(meeting);
            return ResponseEntity.ok(new ApiResponse("Meeting added", meeting));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", null));
        }
    }

    @PutMapping("/meeting/{meetingId}/update")
    public ResponseEntity<ApiResponse> updateMeeting(
            @PathVariable Integer meetingId,
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


}
