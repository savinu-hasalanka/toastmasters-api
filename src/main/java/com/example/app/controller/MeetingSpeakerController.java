package com.example.app.controller;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.MeetingSpeaker;
import com.example.app.request.EvaluatorRequest;
import com.example.app.request.SpeakerRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.meetingSpeaker.MeetingSpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meeting-speaker")
public class MeetingSpeakerController {
    private final MeetingSpeakerService meetingSpeakerService;

    @GetMapping("/get/{meetingId}")
    public ResponseEntity<ApiResponse> getAllSpeakers(@PathVariable Long meetingId) {
        try {
            List<MeetingSpeaker> speakers = meetingSpeakerService.getAllByMeetingId(meetingId);
            return ResponseEntity.ok(new ApiResponse("Success", speakers));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/add/speaker")
    public ResponseEntity<ApiResponse> addSpeaker(@RequestBody SpeakerRequest speaker) {
        try {
            meetingSpeakerService.addSpeaker(speaker);
            return ResponseEntity.ok(new ApiResponse("Speaker added", speaker));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/add/evaluator")
    public ResponseEntity<ApiResponse> addEvaluator(@RequestBody EvaluatorRequest evaluator) {
        try {
            meetingSpeakerService.addEvaluator(evaluator);
            return ResponseEntity.ok(new ApiResponse("Speaker added", evaluator));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (AlreadyExistsException e) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/remove/speaker")
    public ResponseEntity<ApiResponse> removeSpeaker(@RequestBody SpeakerRequest speaker) {
        try {
            meetingSpeakerService.removeSpeaker(speaker);
            return ResponseEntity.ok(new ApiResponse("Speaker removed", speaker));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/remove/evaluator")
    public ResponseEntity<ApiResponse> removeEvaluator(@RequestBody SpeakerRequest speaker) {
        try {
            meetingSpeakerService.removeEvaluator(speaker);
            return ResponseEntity.ok(new ApiResponse("Evaluator removed", speaker));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse("Error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }
}
