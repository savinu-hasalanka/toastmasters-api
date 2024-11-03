package com.example.app.service.meetingSpeaker;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.MeetingSpeaker;
import com.example.app.request.EvaluatorRequest;
import com.example.app.request.SpeakerRequest;

import java.util.List;

public interface IMeetingSpeakerService {
    List<MeetingSpeaker> getAllByMeetingId(Long meetingId);
    MeetingSpeaker addSpeaker(SpeakerRequest request) throws ResourceNotFoundException, AlreadyExistsException;
    MeetingSpeaker addEvaluator(EvaluatorRequest request) throws ResourceNotFoundException, AlreadyExistsException;
//    MeetingSpeaker changeSpeaker(SpeakerRequest request);
//    MeetingSpeaker changeEvaluator(EvaluatorRequest request);
    void removeSpeaker(SpeakerRequest request) throws ResourceNotFoundException;
    void removeEvaluator(SpeakerRequest request) throws ResourceNotFoundException;
}
