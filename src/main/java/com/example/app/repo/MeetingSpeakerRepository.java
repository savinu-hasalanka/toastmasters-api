package com.example.app.repo;

import com.example.app.model.MeetingSpeaker;
import com.example.app.model.composite_keys.MeetingSpeakerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingSpeakerRepository extends JpaRepository<MeetingSpeaker, MeetingSpeakerId> {
    List<MeetingSpeaker> getAllByMeetingId(Long meetingId);

//    boolean existsEvaluatorById(MeetingSpeakerId meetingSpeakerId, String evaluatorUsername);

    boolean existsByIdAndEvaluatorUsername(MeetingSpeakerId meetingSpeakerId, String evaluatorUsername);
}
