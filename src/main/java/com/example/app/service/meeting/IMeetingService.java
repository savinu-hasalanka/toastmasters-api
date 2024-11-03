package com.example.app.service.meeting;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Meeting;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;

import java.util.List;

public interface IMeetingService {
    Meeting addMeeting(AddMeetingRequest request) throws ResourceNotFoundException;
    Meeting updateMeeting(UpdateMeetingRequest request, Long meetingId) throws ResourceNotFoundException;
    List<Meeting> getMeetingByHost(String username) throws ResourceNotFoundException;
}
