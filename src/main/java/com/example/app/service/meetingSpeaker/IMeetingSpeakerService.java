package com.example.app.service.meetingSpeaker;

import com.example.app.model.MeetingSpeaker;
import com.example.app.request.EvaluatorRequest;
import com.example.app.request.SpeakerRequest;

import java.util.List;

public interface IMeetingSpeakerService {
    List<MeetingSpeaker> getAllByMeetingId(Long meetingId);
    MeetingSpeaker addSpeaker(SpeakerRequest request);
    MeetingSpeaker addEvaluator(EvaluatorRequest request);
//    MeetingSpeaker changeSpeaker(SpeakerRequest request);
//    MeetingSpeaker changeEvaluator(EvaluatorRequest request);
    void removeSpeaker(SpeakerRequest request);
    void removeEvaluator(SpeakerRequest request);
}
