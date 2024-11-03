package com.example.app.service.meeting;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Meeting;
import com.example.app.repo.MeetingRepository;
import com.example.app.repo.UserRepository;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService implements IMeetingService {
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public Meeting addMeeting(AddMeetingRequest request) throws ResourceNotFoundException {
        return userRepository.findByUsername(request.getHost())
                .map(appUser -> meetingRepository.save(createMeeting(request, appUser)))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Meeting createMeeting(AddMeetingRequest request, AppUser host) {
        return new Meeting(
                request.getTitle(),
                LocalDate.parse(request.getDate()),
                LocalTime.parse(request.getStartTime()),
                LocalTime.parse(request.getEndTime()),
                host
        );
    }

    @Override
    public Meeting updateMeeting(UpdateMeetingRequest request, Long meetingId) throws ResourceNotFoundException {
        return meetingRepository.findById(meetingId)
                .map(fetchedMeeting -> updateFetchedMeeting(fetchedMeeting, request))
                .map(meetingRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));
    }

    private Meeting updateFetchedMeeting(Meeting meeting, UpdateMeetingRequest request) {
        meeting.setTitle(request.getTitle());
        meeting.setDate(LocalDate.parse(request.getDate()));
        meeting.setStartTime(LocalTime.parse(request.getStartTime()));
        meeting.setEndTime(LocalTime.parse(request.getEndTime()));

        return meeting;
    }

    @Override
    public List<Meeting> getMeetingByHost(String username) throws ResourceNotFoundException {
        AppUser host = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found"));

        return meetingRepository.findByHost(host);
    }

}
