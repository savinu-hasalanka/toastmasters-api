package com.example.app.controller;

import com.example.app.dto.MeetingSpeakerDto;
import com.example.app.dto.SpeakerDto;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.MeetingSpeaker;
import com.example.app.model.types.Speaker;
import com.example.app.request.EvaluatorRequest;
import com.example.app.request.SpeakerRequest;
import com.example.app.response.ApiResponse;
import com.example.app.service.meetingSpeaker.IMeetingSpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/meeting-speaker")
public class MeetingSpeakerController {
    private final IMeetingSpeakerService meetingSpeakerService;

    @GetMapping("/get/{meetingId}")
    public ResponseEntity<ApiResponse> getAllSpeakers(@PathVariable Long meetingId) {
        try {
            List<MeetingSpeaker> speakers = meetingSpeakerService.getAllByMeetingId(meetingId);
            MeetingSpeakerDto meetingSpeakerDto = createMeetingSpeakerDto(meetingId, speakers);
            return ResponseEntity.ok(new ApiResponse("Success", meetingSpeakerDto));
        } catch (Exception e) {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }

    private MeetingSpeakerDto createMeetingSpeakerDto(Long meetingId, List<MeetingSpeaker> speakers) {
        List<SpeakerDto> speakerDtos = new ArrayList<>();

        String speakerUsername;
        String speakerName;
        Speaker speakerType;

        for (MeetingSpeaker speaker : speakers) {
            speakerUsername = speaker.getSpeaker().getUsername();
            speakerName = speaker.getSpeaker().getName();
            speakerType = speaker.getSpeakerType();
            SpeakerDto speakerDto = new SpeakerDto(speakerUsername, speakerName, speakerType);

            if (!(speaker.getEvaluator() == null)) {
                speakerDto.setEvaluatorUsername(speaker.getEvaluator().getUsername());
                speakerDto.setEvaluatorName(speaker.getEvaluator().getName());
            }

            speakerDtos.add(speakerDto);
        }
        return new MeetingSpeakerDto(meetingId, speakerDtos);

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
