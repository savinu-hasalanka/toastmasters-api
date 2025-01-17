package com.example.app.service.meeting;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.Club;
import com.example.app.model.Meeting;
import com.example.app.repo.ClubRepository;
import com.example.app.repo.MeetingRepository;
import com.example.app.request.AddMeetingRequest;
import com.example.app.request.UpdateMeetingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService implements IMeetingService {

    private final MeetingRepository meetingRepository;
    private final ClubRepository clubRepository;

    @Override
    @Transactional
    public void addMeeting(AddMeetingRequest request) {
        Optional<Club> visitingClub = Optional.ofNullable(request.getVisitingClubId())
                .flatMap(clubRepository::findById);

        Meeting meeting = new Meeting(
                request.getTitle(),
                LocalDate.parse(request.getDate()),
                LocalTime.parse(request.getStartTime()),
                LocalTime.parse(request.getEndTime()),
                request.getLocation(),
                request.getStatus(),
                request.getClub(),
                visitingClub.orElse(null)
        );

        meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingById(Integer meetingId) throws ResourceNotFoundException {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));
    }

    @Override
    public List<Meeting> getMeetingsByClubId(Integer clubId) throws ResourceNotFoundException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        return meetingRepository.findAllByClubOrVisitingClub(club, club);
    }

    @Override
    @Transactional
    public void updateMeeting(UpdateMeetingRequest updateRequest, Integer meetingId) throws ResourceNotFoundException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found!"));


        Optional<Club> visitingClub = Optional.ofNullable(updateRequest.getVisitingClubId())
                .flatMap(clubRepository::findById);

        meeting.setTitle(updateRequest.getTitle());
        meeting.setDate(LocalDate.parse(updateRequest.getDate()));
        meeting.setStartTime(LocalTime.parse(updateRequest.getStartTime()));
        meeting.setEndTime(LocalTime.parse(updateRequest.getEndTime()));
        meeting.setLocation(updateRequest.getLocation());
        meeting.setStatus(updateRequest.getStatus());
        meeting.setVisitingClub(visitingClub.orElse(null));

        meetingRepository.save(meeting);
    }

}
