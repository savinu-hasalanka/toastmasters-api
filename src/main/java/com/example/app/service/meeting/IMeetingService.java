package com.example.app.service.meeting;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Meeting;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;

import java.util.List;

public interface IMeetingService {

    void addMeeting(AddMeetingRequest request);

    Meeting getMeetingById(Integer meetingId) throws ResourceNotFoundException;

    List<Meeting> getMeetingsByClubId(Integer clubId) throws ResourceNotFoundException;

    void updateMeeting(UpdateMeetingRequest meeting, Integer meetingId) throws ResourceNotFoundException;
}
