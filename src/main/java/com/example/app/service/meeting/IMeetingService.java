package com.example.app.service.meeting;

import com.example.app.model.Meeting;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;

import java.util.List;

public interface IMeetingService {
    Meeting addMeeting(AddMeetingRequest request);
    Meeting updateMeeting(UpdateMeetingRequest request, Long meetingId);
    List<Meeting> getMeetingByHost(String username);
}
