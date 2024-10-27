package com.example.app.service.meetingSpeaker;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Meeting;
import com.example.app.model.MeetingSpeaker;
import com.example.app.model.composite_keys.MeetingSpeakerId;
import com.example.app.model.types.Speaker;
import com.example.app.repo.MeetingRepository;
import com.example.app.repo.MeetingSpeakerRepository;
import com.example.app.repo.UserRepository;
import com.example.app.request.EvaluatorRequest;
import com.example.app.request.SpeakerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingSpeakerService implements IMeetingSpeakerService {
    private final MeetingSpeakerRepository meetingSpeakerRepository;
    private final UserRepository userRepository;
    private final MeetingRepository meetingRepository;

    @Override
    public List<MeetingSpeaker> getAllByMeetingId(Long meetingId) {
        return meetingSpeakerRepository.getAllByMeetingId(meetingId);
    }

    @Override
    public MeetingSpeaker addSpeaker(SpeakerRequest request) {
        Long meetingId = request.getMeetingId();
        String speakerUsername = request.getSpeakerUsername();
        Speaker speakerType = request.getSpeakerType();

        Meeting meeting = meetingRepository
                .findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        AppUser speaker = userRepository
                .findByUsername(speakerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found"));

        MeetingSpeakerId meetingSpeakerId = new MeetingSpeakerId(meetingId, speakerUsername, speakerType);

        if (meetingSpeakerRepository.existsById(meetingSpeakerId))
            throw new AlreadyExistsException(speakerUsername + " already exists as a " + speakerType);

        return meetingSpeakerRepository.save(new MeetingSpeaker(meetingSpeakerId, meeting, speaker));
    }

    @Override
    public MeetingSpeaker addEvaluator(EvaluatorRequest request) {
        Long meetingId = request.getMeetingId();
        String speakerUsername = request.getSpeakerUsername();
        Speaker speakerType = request.getSpeakerType();
        String evaluatorUsername = request.getEvaluatorUsername();

        Meeting meeting = meetingRepository
                .findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        AppUser speaker = userRepository
                .findByUsername(speakerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found"));

        AppUser evaluator = userRepository
                .findByUsername(evaluatorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found"));

        MeetingSpeakerId meetingSpeakerId = new MeetingSpeakerId(meetingId, speakerUsername, speakerType);


        if (meetingSpeakerRepository.existsByIdAndEvaluatorUsername(meetingSpeakerId, evaluatorUsername))
            throw new AlreadyExistsException(evaluatorUsername + " already exists as an evaluator for " + speakerType + " " + speakerUsername);

        return meetingSpeakerRepository.save(new MeetingSpeaker(meetingSpeakerId, meeting, speaker, evaluator));
    }

    @Override
    public void removeSpeaker(SpeakerRequest request) {
        Long meetingId = request.getMeetingId();
        String speakerUsername = request.getSpeakerUsername();
        Speaker speakerType = request.getSpeakerType();

        meetingSpeakerRepository.findById(new MeetingSpeakerId(meetingId, speakerUsername, speakerType))
                .ifPresentOrElse(
                        meetingSpeakerRepository::delete,
                        () -> {throw new ResourceNotFoundException("meeting-speaker not found");}
                );
    }

    @Override
    public void removeEvaluator(SpeakerRequest request) {
        Long meetingId = request.getMeetingId();
        String speakerUsername = request.getSpeakerUsername();
        Speaker speakerType = request.getSpeakerType();
        MeetingSpeakerId meetingSpeakerId = new MeetingSpeakerId(meetingId, speakerUsername, speakerType);

        Meeting meeting = meetingRepository
                .findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        AppUser speaker = userRepository
                .findByUsername(speakerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found"));


        meetingSpeakerRepository.findById(meetingSpeakerId)
                .ifPresentOrElse(
                        existingMeetingSpeaker -> meetingSpeakerRepository.save(new MeetingSpeaker(meetingSpeakerId, meeting, speaker, null)),
                        () -> {throw new ResourceNotFoundException("No record found");}
                );
    }
}
